package com.lisade.togeduck.config;

import com.lisade.togeduck.batch.FestivalIdRangePartitioner;
import com.lisade.togeduck.batch.FestivalItem;
import com.lisade.togeduck.batch.FestivalItemProcessingResult;
import com.lisade.togeduck.batch.PopularFestivalCacheUpdateTasklet;
import com.lisade.togeduck.cache.service.BestFestivalCacheService;
import com.lisade.togeduck.cache.service.FestivalClickCountCacheService;
import com.lisade.togeduck.cache.service.PopularFestivalCacheService;
import com.lisade.togeduck.cache.value.FestivalClickCountCacheValue;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.CategoryRepository;
import com.lisade.togeduck.repository.FestivalRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class PopularScoringBatchConfig {

    public static final double GRAVITY_CONSTANT = 1.8;
    public static final int POOL_SIZE = 2;

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final FestivalClickCountCacheService festivalClickCountCacheService;
    private final CategoryRepository categoryRepository;
    private final PopularFestivalCacheService popularFestivalCacheService;
    private final BestFestivalCacheService bestFestivalCacheService;
    private final FestivalMapper festivalMapper;
    private final FestivalRepository festivalRepository;

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Job saveClickCountAndCalcPopularScoreJob(JobRepository jobRepository) {
        return new JobBuilder("calcPopularScoreJob", jobRepository)
            .start(saveClickCountStep(jobRepository))
            .next(calcPopularScoreStepManager(jobRepository))
            .next(confirmPopularFestivalStep(jobRepository))
            .build();
    }

    @Bean
    public Step saveClickCountStep(JobRepository jobRepository) {
        return new StepBuilder("saveClickCount", jobRepository)
            .<FestivalClickCountCacheValue, FestivalClickCountCacheValue>chunk(1000,
                transactionManager())
            .reader(festivalClickCountItemReader())
            .writer(festivalClickCountItemWriter())
            .build();
    }

    @Bean
    public Step calcPopularScoreStepManager(JobRepository jobRepository) {
        return new StepBuilder("calcPopularScore.StepManager", jobRepository)
            .partitioner("calcPopularScoreStep", partitioner())
            .taskExecutor(executor())
            .partitionHandler(partitionHandler(jobRepository))
            .build();
    }

    @Bean
    public Step calcPopularScoreStep(JobRepository jobRepository) {
        return new StepBuilder("calcPopularScoreStep", jobRepository)
            .<FestivalItem, FestivalItemProcessingResult>chunk(1000, transactionManager())
            .reader(festivalItemReader(null, null))
            .processor(calcPopularScoreProcessor())
            .writer(scoreItemWriter())
            .build();
    }

    @Bean
    public Step confirmPopularFestivalStep(JobRepository jobRepository) {
        return new StepBuilder("confirmPopularFestivalStep", jobRepository)
            .tasklet(new PopularFestivalCacheUpdateTasklet(festivalRepository, categoryRepository,
                    popularFestivalCacheService, bestFestivalCacheService, festivalMapper),
                transactionManager()).build();
    }

    @Bean
    @StepScope
    public ListItemReader<FestivalClickCountCacheValue> festivalClickCountItemReader() {
        List<FestivalClickCountCacheValue> festivalClickCountCacheValues = new ArrayList<>();
        festivalClickCountCacheService.getAll().forEach(festivalClickCountCacheValues::add);
        if (!festivalClickCountCacheValues.isEmpty()) {
            festivalClickCountCacheService.deleteAll();
        }
        return new ListItemReader<>(festivalClickCountCacheValues);
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<FestivalItem> festivalItemReader(
        @Value("#{stepExecutionContext[minId]}") Long minId,
        @Value("#{stepExecutionContext[maxId]}") Long maxId) {
        log.info("reader minId={}, maxId={}", minId, maxId);
        return new JdbcCursorItemReaderBuilder<FestivalItem>()
            .name("festivalItemReader")
            .sql(
                "select f.id as id, sum(fv.count) as weeklyViews, sum(r.number_of_seats) as totalSeats, sum(r.number_of_reservation_seats) as totalReservationSeats, f.created_at as createdAt "
                    + "from festival as f "
                    + "inner join festival_view as fv "
                    + "on f.id = fv.festival_id "
                    + "inner join route as r "
                    + "on f.id = r.festival_id "
                    + "WHERE f.id BETWEEN ? AND ? "
                    + "and fv.measurement_at > ?"
                    + "group by f.id;")
            .beanRowMapper(FestivalItem.class)
            .queryArguments(minId, maxId, LocalDate.now().minusWeeks(1L))
            .dataSource(dataSource)
            .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<FestivalItem, FestivalItemProcessingResult> calcPopularScoreProcessor() {
        return (item) -> FestivalItemProcessingResult.of(item.getId(), getTotalScore(item));
    }

    @Bean
    @StepScope
    public ItemWriter<FestivalClickCountCacheValue> festivalClickCountItemWriter() {
        String sql = "INSERT INTO festival_view (festival_id, count, measurement_at) "
            + "VALUES (?, ?, ?) "
            + "ON DUPLICATE KEY UPDATE "
            + "count = count + ?";
        return items -> {
            log.info("festival_view 업데이트 시작");
            LocalDate now = LocalDate.now();
            jdbcTemplate.batchUpdate(sql, items.getItems(), items.size(),
                (ps, argument) -> {
                    ps.setLong(1, argument.getFestivalId());
                    ps.setInt(2, argument.getClickCount());
                    ps.setDate(3, Date.valueOf(now));
                    ps.setInt(4, argument.getClickCount());
                });
            log.info("festival_view 업데이트 완료");
        };
    }

    @Bean
    @StepScope
    public ItemWriter<FestivalItemProcessingResult> scoreItemWriter() {
        String sql = "UPDATE festival SET popular_score = ? WHERE id = ?";
        log.info("점수 update 시작");
        return items -> {
            jdbcTemplate.batchUpdate(sql, items.getItems(), items.size(),
                (ps, argument) -> {
                    ps.setDouble(1, argument.getScore());
                    ps.setLong(2, argument.getId());
                });
        };
    }

    @Bean
    @StepScope
    public FestivalIdRangePartitioner partitioner() {
        return new FestivalIdRangePartitioner(festivalRepository);
    }

    @Bean(name = "calcPopularScoreJob_partitionHandler")
    public TaskExecutorPartitionHandler partitionHandler(JobRepository jobRepository) {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setStep(calcPopularScoreStep(jobRepository));
        partitionHandler.setTaskExecutor(executor());
        partitionHandler.setGridSize(POOL_SIZE);
        return partitionHandler;
    }

    @Bean(name = "calcPopularScoreJob_taskPool")
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(POOL_SIZE);
        executor.setMaxPoolSize(POOL_SIZE);
        executor.setThreadNamePrefix("partition-thread");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();
        return executor;
    }

    private Double getTotalScore(FestivalItem festivalItem) {
        Double viewScore = getViewScore(festivalItem);
        Double reservationScore = getReservationRatioScore(festivalItem);
        return viewScore + reservationScore;
    }

    private Double getReservationRatioScore(FestivalItem festivalItem) {
        Long totalSeats = festivalItem.getTotalSeats();
        Long reservedSeats = festivalItem.getTotalReservationSeats();
        if (totalSeats == null || totalSeats == 0) {
            return 0.0;
        }

        Double ratio = (double) reservedSeats / totalSeats;
        long hours =
            festivalItem.getCreatedAt().toInstant(ZoneOffset.UTC).getEpochSecond() / 3600 + 2;
        Double time = Math.pow(hours, GRAVITY_CONSTANT);
        return ratio / time;
    }

    private Double getViewScore(FestivalItem festivalItem) {
        long hours =
            festivalItem.getCreatedAt().toInstant(ZoneOffset.UTC).getEpochSecond() / 3600 + 2;
        Double time = Math.pow(hours, GRAVITY_CONSTANT);
        return festivalItem.getWeeklyViews() / time;
    }
}

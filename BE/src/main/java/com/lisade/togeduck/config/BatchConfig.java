package com.lisade.togeduck.config;

import com.lisade.togeduck.batch.FestivalItem;
import com.lisade.togeduck.batch.FestivalItemProcessingResult;
import java.time.LocalDate;
import java.time.ZoneOffset;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    public static final double GRAVITY_CONSTANT = 1.8;

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Job saveClickCountAndCalcPopularScoreJob(JobRepository jobRepository) {
        return new JobBuilder("calcPopularScoreJob", jobRepository)
            .start(calcPopularScoreStep(jobRepository))
            .build();
    }

    @Bean
    public Step calcPopularScoreStep(JobRepository jobRepository) {
        return new StepBuilder("calcPopularScoreStep", jobRepository)
            .<FestivalItem, FestivalItemProcessingResult>chunk(1000, transactionManager())
            .reader(festivalItemReader())
            .processor(calcPopularScoreProcessor())
            .writer(scoreItemWriter())
            .build();
    }

    @Bean
    public JdbcCursorItemReader<FestivalItem> festivalItemReader() {
        return new JdbcCursorItemReaderBuilder<FestivalItem>()
            .name("festivalItemReader")
            .sql(
                "select f.id as id, sum(fv.count) as weeklyViews, sum(r.number_of_seats) as totalSeats, sum(r.number_of_reservation_seats) as totalReservationSeats, f.created_at as createdAt "
                    + "from festival as f "
                    + "inner join festival_view as fv "
                    + "on f.id = fv.festival_id "
                    + "inner join route as r "
                    + "on f.id = r.festival_id "
                    + "where fv.measurement_at > ?"
                    + "group by f.id;")
            .beanRowMapper(FestivalItem.class)
            .queryArguments(LocalDate.now().minusWeeks(1L))
            .fetchSize(1000)
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public ItemProcessor<FestivalItem, FestivalItemProcessingResult> calcPopularScoreProcessor() {
        return (item) -> FestivalItemProcessingResult.of(item.getId(), getTotalScore(item));
    }

    @Bean
    public ItemWriter<FestivalItemProcessingResult> scoreItemWriter() {
        String sql = "UPDATE festival SET popular_score = ? WHERE id = ?";

        return items -> {
            jdbcTemplate.batchUpdate(sql, items.getItems(), items.size(),
                (ps, argument) -> {
                    ps.setDouble(1, argument.getScore());
                    ps.setLong(2, argument.getId());
                });
        };
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

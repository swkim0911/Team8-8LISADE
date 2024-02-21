package com.lisade.togeduck.config;

import com.lisade.togeduck.dto.BatchDto;
import com.lisade.togeduck.dto.BatchProcessingResultDto;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    public static final double GRAVITY_CONSTANT = 1.8;
    private final DataSource dataSource;

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Job calcPopularScoreJob(JobRepository jobRepository) {
        return new JobBuilder("calcPopularScoreJob", jobRepository)
            .start(step(jobRepository))
            .build();
    }

    @Bean
    public Step step(JobRepository jobRepository) {
        // TODO ItemReader, ItemProcessor, ItemWriter 구현하기

        return new StepBuilder("step", jobRepository)
            .<BatchDto, BatchProcessingResultDto>chunk(1000, transactionManager())
            .reader(itemReader())
            .writer(itemWriter())
            .build();
    }

    @Bean
    public JdbcCursorItemReader<BatchDto> itemReader() {
        return new JdbcCursorItemReaderBuilder<BatchDto>()
            .name("itemReader")
            .sql(
                "select f.id as id, sum(fv.count) as weeklyViews, sum(r.number_of_seats) as totalSeats, sum(r.number_of_reservation_seats) as totalReservationSeats, f.created_at as createdAt "
                    + "from festival as f "
                    + "inner join festival_view as fv "
                    + "on f.id = fv.festival_id "
                    + "inner join route as r "
                    + "on f.id = r.festival_id "
                    + "where fv.measurement_at > ?"
                    + "group by f.id;")
            .beanRowMapper(BatchDto.class)
            .queryArguments(LocalDate.now().minusWeeks(1L))
            .fetchSize(1000)
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public ItemProcessor<BatchDto, BatchProcessingResultDto> calcPopularScoreProcessor() {
        return (item) -> BatchProcessingResultDto.of(item.getId(), getTotalScore(item));
    }

    @Bean
    public ItemWriter<BatchProcessingResultDto> itemWriter() {
        return items -> {
            System.out.println(items.size());
        };
    }

    private Double getTotalScore(BatchDto batchDto) {
        Double viewScore = getViewScore(batchDto);
        Double reservationScore = getReservationRatioScore(batchDto);
        return viewScore + reservationScore;
    }

    private Double getReservationRatioScore(BatchDto batchDto) {
        Long totalSeats = batchDto.getTotalSeats();
        Long reservedSeats = batchDto.getTotalReservationSeats();
        if (totalSeats == null || totalSeats == 0) {
            return 0.0;
        }

        Double ratio = (double) reservedSeats / totalSeats;
        long hours = batchDto.getCreatedAt().toInstant(ZoneOffset.UTC).getEpochSecond() / 3600 + 2;
        Double time = Math.pow(hours, GRAVITY_CONSTANT);
        return ratio / time;
    }

    private Double getViewScore(BatchDto batchDto) {
        long hours = batchDto.getCreatedAt().toInstant(ZoneOffset.UTC).getEpochSecond() / 3600 + 2;
        Double time = Math.pow(hours, GRAVITY_CONSTANT);
        return batchDto.getWeeklyViews() / time;
    }
}

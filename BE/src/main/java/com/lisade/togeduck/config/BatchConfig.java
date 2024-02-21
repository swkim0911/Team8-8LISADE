package com.lisade.togeduck.config;

import com.lisade.togeduck.dto.BatchDto;
import java.time.LocalDate;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
            .<BatchDto, BatchDto>chunk(1000, transactionManager())
            .reader(itemReader())
            .writer(itemWriter())
            .build();
    }

    @Bean
    public JdbcCursorItemReader<BatchDto> itemReader() {
        return new JdbcCursorItemReaderBuilder<BatchDto>()
            .name("itemReader")
            .sql(
                "select f.id as id, f.title as title, sum(fv.count) as count "
                    + "from festival as f "
                    + "inner join festival_view as fv "
                    + "on f.id = fv.festival_id "
                    + "where fv.measurement_at > ?"
                    + "group by f.id;")
            .beanRowMapper(BatchDto.class)
            .queryArguments(LocalDate.now().minusWeeks(1L))
            .fetchSize(1000)
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public ItemWriter<BatchDto> itemWriter() {
        return items -> {
            System.out.println(items.size());
        };
    }
}

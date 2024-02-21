package com.lisade.togeduck.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
            .chunk(1000, transactionManager())
            .build();
    }
}

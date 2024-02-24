package com.lisade.togeduck.config;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchSchedulerConfig {

    private final Job job;
    private final JobLauncher jobLauncher;

    @Scheduled(fixedDelay = 300000)
    public void batchSchedule() {
        JobParameter<?> jobParameter = new JobParameter<>(System.currentTimeMillis(), Long.class);

        Map<String, JobParameter<?>> confMap = Map.of("time", jobParameter);
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }
}

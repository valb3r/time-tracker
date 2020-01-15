package ua.timetracker.reportgenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobTest {

    private final JobRepository jobRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @SneakyThrows
    //@Scheduled(initialDelay = 0, fixedDelay = 1000000)
    void runJob() {
        val job = jobBuilderFactory.get("FOO")
            .start(stepBuilderFactory.get("ONE").tasklet((a, b) -> {
                log.info("STEP ONE!");
                return null;
            }).build())
            .next(stepBuilderFactory.get("TWO").tasklet((a, b) -> {
                log.info("STEP TWO!");
                return null;
            }).build())
            .build();

        val exec = jobRepository.createJobExecution("AAA!", new JobParameters());
        job.execute(exec);
    }
}

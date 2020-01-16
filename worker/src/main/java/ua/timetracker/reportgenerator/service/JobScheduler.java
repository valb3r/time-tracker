package ua.timetracker.reportgenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.timetracker.reportgenerator.persistence.repository.imperative.ReportsRepository;
import ua.timetracker.shared.persistence.entity.report.Report;
import ua.timetracker.shared.persistence.entity.report.ReportStatus;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobScheduler {

    private final ThreadPoolTaskExecutor jobExecutor;

    private final ReportsRepository reports;

    private final JobRepository jobRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @SneakyThrows
    @Scheduled(fixedRateString = "${jobs.schedule.runner}")
    @Transactional
    public void runJob() {

        int availableExecs = jobExecutor.getCorePoolSize() - jobExecutor.getActiveCount();
        if (availableExecs <= 0) {
            return;
        }

        reports.findAllByStatus(ReportStatus.SCHEDULED, availableExecs)
            .forEach(it -> {
                it.setStatus(ReportStatus.PROCESSING);
                reports.save(it);
                jobExecutor.execute(this::runJob);
            });
    }

    private void runJob(Report report) {
        try {
            val jobName = report.getJob() + "-" + report.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);

            val job = jobBuilderFactory.get(jobName)
                .start(stepBuilderFactory.get("CREATE_REPORT").tasklet((a, b) -> {
                    log.info("STEP ONE!");
                    return null;
                }).build())
                .next(stepBuilderFactory.get("FINALIZE_REPORT").tasklet((a, b) -> {
                    log.info("STEP TWO!");
                    return null;
                }).build())
                .build();

            val exec = jobRepository.createJobExecution(jobName, new JobParameters());
            job.execute(exec);
        } catch (Exception ex) {
            log.error("Failed for {}:{}", report.getId(), report.getJob(), ex);
            throw new RuntimeException(ex);
        }
    }
}

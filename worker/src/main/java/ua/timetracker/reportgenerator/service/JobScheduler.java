package ua.timetracker.reportgenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.timetracker.reportgenerator.persistence.repository.imperative.ReportsRepository;
import ua.timetracker.shared.persistence.entity.report.Report;
import ua.timetracker.shared.persistence.entity.report.ReportStatus;
import ua.timetracker.shared.persistence.entity.report.ReportType;

import java.time.format.DateTimeFormatter;

import static ua.timetracker.reportgenerator.service.Const.REPORT_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobScheduler {

    private final ThreadPoolTaskExecutor jobExecutor;

    private final ReportsRepository reports;

    private final JobRepository jobRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ReportByProjectJob byProjectJob;
    private final ReportByUserJob byUserJob;

    @SneakyThrows
    @Scheduled(cron = "${jobs.schedule.runner}")
    @Transactional
    public void scheduleJob() {

        int availableExecs = jobExecutor.getCorePoolSize() - jobExecutor.getActiveCount();
        if (availableExecs <= 0) {
            return;
        }

        reports.findAllByStatus(ReportStatus.SCHEDULED, availableExecs)
            .stream()
            .forEach(it -> {
                log.info("Picking {} for execution", it.getId());
                it.setStatus(ReportStatus.PROCESSING);
                reports.save(it);
                jobExecutor.execute(() -> runJob(it));
            });
    }

    private void runJob(Report report) {
        String oldName = Thread.currentThread().getName();
        Thread.currentThread().setName(report.getJob() + "-" + report.getId());
        try {
            val jobName = report.getJob() + "-" + report.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);
            val params = new JobParametersBuilder().addLong(REPORT_ID, report.getId()).toJobParameters();

            val job = jobBuilderFactory.get(jobName)
                .start(buildInitialStep(report).build())
                .next(stepBuilderFactory.get("FINALIZE_REPORT")
                    .tasklet((contribution, chunkContext) -> {
                        Report updated = reports.findById(report.getId()).get();
                        updated.setStatus(ReportStatus.DONE);
                        reports.save(updated);
                        return RepeatStatus.FINISHED;
                }).build())
                .build();

            val exec = jobRepository.createJobExecution(jobName, params);
            job.execute(exec);
        } catch (Exception ex) {
            log.error("Failed for {}:{}", report.getId(), report.getJob(), ex);

            Report updated = reports.findById(report.getId()).get();
            updated.setStatus(ReportStatus.FAILED);
            reports.save(updated);

            throw new RuntimeException(ex);
        } finally {
            Thread.currentThread().setName(oldName);
        }
    }

    private TaskletStepBuilder buildInitialStep(Report report) {
        if (report.getType() == ReportType.BY_PROJECT) {
            return stepBuilderFactory.get("CREATE_REPORT").tasklet(byProjectJob);
        } else if (report.getType() == ReportType.BY_USER) {
            return stepBuilderFactory.get("CREATE_REPORT").tasklet(byUserJob);
        } else {
            throw new IllegalStateException("Wrong report type: " + report.getType());
        }
    }
}

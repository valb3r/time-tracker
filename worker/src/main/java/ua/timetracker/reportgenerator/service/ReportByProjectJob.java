package ua.timetracker.reportgenerator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;
import ua.timetracker.reportgenerator.persistence.repository.imperative.ProjectsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.ReportTemplatesRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.ReportsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.TimeLogsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.dto.DevAndCardDto;
import ua.timetracker.shared.persistence.entity.report.Report;
import ua.timetracker.shared.persistence.entity.report.ReportTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ua.timetracker.reportgenerator.service.Const.REPORT_ID;
import static ua.timetracker.reportgenerator.service.Util.getExecution;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportByProjectJob implements Tasklet {

    private final ReportTemplatesRepository templates;
    private final ReportsRepository reports;
    private final ProjectsRepository projects;
    private final TimeLogsRepository logs;

    @Override
    @SneakyThrows
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        Report report = reports.findById(getExecution(chunkContext).getJobParameters().getLong(REPORT_ID)).get();

        List<ProjectDevelopersCards> projectCards = new ArrayList<>();
        for (val projectId : report.getTargets()) {
            val project = projects.findById(projectId).get();
            ProjectDevelopersCards result = new ProjectDevelopersCards();
            result.setName(project.getName());

            val allLogs = logs.allLogsForProject(projectId, report.getFrom(), report.getTo());

            Map<Long, DevelopersCards> devToCards = new LinkedHashMap<>();
            allLogs.forEach(it -> {
                val devCard = devToCards.computeIfAbsent(
                    it.getUser().getId(),
                    id -> new DevelopersCards()
                );

                devCard.setName(it.getUser().getUsername());
                val card = new ReportByProjectJob.Card();
                card.setDate(it.getLog().getTimestamp());
                card.setActivity(it.getLog().getTags().iterator().next());
                card.setDescription(it.getLog().getDescription());
                card.setDuration(hoursBilled(it));
                devCard.getCards().add(card);
                devCard.setDuration(devCard.getDuration().add(card.getDuration()));
            });

            result.developers.addAll(devToCards.values());
            projectCards.add(result);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Context context = new Context();
        context.putVar("projects", projectCards);
        context.putVar("report", new ProjectReport(report.getFrom(), report.getTo()));

        // broken Relationship annotation due to rx-sdn -> ogm bridge
        ReportTemplate template = templates.getByReport(report.getId());
        JxlsHelper.getInstance().processTemplate(
            new ByteArrayInputStream(Base64.getDecoder().decode(template.getTemplate())),
            bos,
            context
        );

        report.setResult(Base64.getEncoder().encodeToString(bos.toByteArray()));
        reports.save(report);
        return RepeatStatus.FINISHED;
    }

    private BigDecimal hoursBilled(DevAndCardDto it) {
        return BigDecimal.valueOf(it.getLog().getDuration().toMinutes())
            .divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_EVEN);
    }

    @Data
    public static class ProjectDevelopersCards {

        private String name;
        private List<DevelopersCards> developers = new ArrayList<>();
    }

    @Data
    public static class DevelopersCards {

        private String name;
        private BigDecimal duration = BigDecimal.ZERO;
        private List<ReportByProjectJob.Card> cards = new ArrayList<>();
    }

    @Data
    public static class Card {

        private LocalDateTime date;
        private String activity;
        private String description;
        private BigDecimal duration;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectReport {

        private LocalDateTime start;
        private LocalDateTime end;
    }
}

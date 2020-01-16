package ua.timetracker.reportgenerator.service;

import lombok.Data;
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
import ua.timetracker.reportgenerator.persistence.repository.imperative.ReportTemplatesRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.ReportsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.TimeLogsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.UsersRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.dto.ProjectAndCardDto;
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
public class ReportByUserJob implements Tasklet {

    private final ReportTemplatesRepository templates;
    private final ReportsRepository reports;
    private final UsersRepository users;
    private final TimeLogsRepository logs;

    @SneakyThrows
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        Report report = reports.findById(getExecution(chunkContext).getJobParameters().getLong(REPORT_ID)).get();

        List<DeveloperProjectCards> developerCards = new ArrayList<>();
        for (val userId : report.getTargets()) {
            val user = users.findById(userId).get();
            DeveloperProjectCards result = new DeveloperProjectCards();
            result.setName(user.getUsername());

            val allLogs = logs.allLogsForUser(userId, report.getFrom(), report.getTo());

            Map<Long, ProjectCards> projToCards = new LinkedHashMap<>();
            allLogs.forEach(it -> {
                val projCard = projToCards.computeIfAbsent(
                    it.getProject().getId(),
                    id -> new ProjectCards()
                );

                projCard.setName(it.getProject().getName());
                projCard.setCode(it.getProject().getCode());
                val card = new Card();
                card.setDate(it.getLog().getTimestamp());
                card.setActivity(it.getLog().getTags().iterator().next());
                card.setDescription(it.getLog().getDescription());
                card.setDuration(hoursBilled(it));
                projCard.getCards().add(card);
                projCard.setDuration(projCard.getDuration().add(card.getDuration()));
            });

            result.projects.addAll(projToCards.values());
            developerCards.add(result);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Context context = new Context();
        context.putVar("developers", developerCards);

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

    private BigDecimal hoursBilled(ProjectAndCardDto it) {
        return BigDecimal.valueOf(it.getLog().getDuration().toMinutes())
            .divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_EVEN);
    }

    @Data
    public static class DeveloperProjectCards {

        private String name;
        private List<ProjectCards> projects = new ArrayList<>();
    }

    @Data
    public static class ProjectCards {

        private String name;
        private String code;
        private BigDecimal duration = BigDecimal.ZERO;
        private List<Card> cards = new ArrayList<>();
    }

    @Data
    public static class Card {

        private LocalDateTime date;
        private String activity;
        private String description;
        private BigDecimal duration;
    }
}

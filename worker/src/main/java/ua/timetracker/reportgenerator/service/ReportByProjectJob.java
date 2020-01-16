package ua.timetracker.reportgenerator.service;

import com.google.common.collect.ImmutableList;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.codec.Resources;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;
import ua.timetracker.reportgenerator.persistence.repository.imperative.ProjectsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.TimeLogsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.dto.DevAndCardDto;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportByProjectJob {

    private final ProjectsRepository projects;
    private final TimeLogsRepository logs;

    @SneakyThrows
    //@Scheduled(initialDelay = 0, fixedDelay = 1000000)
    public void createByProjectExecution() {
        List<Long> projectIds = ImmutableList.of(10L, 11L);

        List<ProjectDevelopersCards> projectCards = new ArrayList<>();
        for (val projectId : projectIds) {
            val project = projects.findById(projectId).get();
            ProjectDevelopersCards result = new ProjectDevelopersCards();
            result.setName(project.getName());

            val allLogs = logs.allLogsForProject(
                    projectId,
                    LocalDateTime.parse("2010-01-01T00:00:00"),
                    LocalDateTime.parse("2030-01-01T00:00:00"));

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

        try(InputStream is = Resources.getInputStream("by-project.xlsx")) {
            try (OutputStream os = new FileOutputStream("/home/valb3r/Documents/OpenSource/time-tracker/report-generator/src/main/resources/object_collection_output.xlsx")) {
                Context context = new Context();
                context.putVar("projects", projectCards);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
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
}

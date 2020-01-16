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
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.timetracker.reportgenerator.persistence.repository.imperative.TimeLogsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.UsersRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.dto.ProjectAndCardDto;

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
public class ReportByUserJob {

    private final UsersRepository users;
    private final TimeLogsRepository logs;

    private final JobRepository jobRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @SneakyThrows
    @Scheduled(initialDelay = 0, fixedDelay = 1000000)
    public void createByProjectExecution() {

        List<Long> userIds = ImmutableList.of(16L, 18L);

        List<DeveloperProjectCards> developerCards = new ArrayList<>();
        for (val userId : userIds) {
            val user = users.findById(userId).get();
            DeveloperProjectCards result = new DeveloperProjectCards();
            result.setName(user.getUsername());

            val allLogs = logs.allLogsForUser(
                userId,
                LocalDateTime.parse("2010-01-01T00:00:00"),
                LocalDateTime.parse("2030-01-01T00:00:00"));

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

        try(InputStream is = Resources.getInputStream("by-developer.xlsx")) {
            try (OutputStream os = new FileOutputStream("/home/valb3r/Documents/OpenSource/time-tracker/report-generator/src/main/resources/object_dev.xlsx")) {
                Context context = new Context();
                context.putVar("developers", developerCards);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
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

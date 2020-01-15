package ua.timetracker.reportgenerator.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.timetracker.reportgenerator.persistence.repository.imperative.ProjectsRepository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.TimeLogsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportByProjectJob {

    private final ProjectsRepository projects;
    private final TimeLogsRepository logs;

    private final JobRepository jobRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Scheduled(initialDelay = 0, fixedDelay = 1000000)
    public void createByProjectExecution() {
        val project = projects.findById(10L).get();

        val allLogs = logs.allLogsForProject(
                10,
                "2010-01-01T00:00:00",
               "2030-01-01T00:00:00"
        );

        System.out.println("PROJECT " + project.getCode());
        System.out.println(allLogs.get(0).getUser().getFullname());

        /*List<Long> projectIds = ImmutableList.of(10L);

        List<ProjectDevelopersCards> projectCards = new ArrayList<>();

        for (val projectId : projectIds) {
            val project = projects.simpleFindById(projectId).get("project");
            ProjectDevelopersCards result = new ProjectDevelopersCards();
            result.setName(project.get("name"));

            val allLogs = logs.allLogsForProject(
                    10,
                    LocalDateTime.parse("2010-01-01T00:00:00"),
                    LocalDateTime.parse("2020-01-01T00:00:00"));

            Map<Long, DevelopersCards> devToCards = new HashMap<>();
            allLogs.forEach(it -> {
                val devCard = devToCards.computeIfAbsent(
                        it.get("dev").get("id"),
                        id -> new DevelopersCards()
                );

                devCard.setName(it.get("dev").get("name"));
                val card = new ReportByProjectJob.Card();
                card.setDate(it.get("dev").get("timestamp"));
                card.setActivity((String) it.get("dev").get("tags").asList().get(0));
                card.setDescription(it.get("dev").get("description").asString());
            });

            projectCards.add(result);
        }

        int i = 0;*/
    }

    @Data
    private static class ProjectDevelopersCards {

        private String name;
        private List<DevelopersCards> developers = new ArrayList<>();
    }

    @Data
    private static class DevelopersCards {

        private String name;
        private List<ReportByProjectJob.Card> cards;
    }

    @Data
    private static class Card {

        private LocalDateTime date;
        private String activity;
        private String description;
    }
}

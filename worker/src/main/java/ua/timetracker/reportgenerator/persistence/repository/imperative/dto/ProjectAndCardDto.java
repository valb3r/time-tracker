package ua.timetracker.reportgenerator.persistence.repository.imperative.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.annotation.QueryResult;
import ua.timetracker.shared.persistence.entity.projects.Project;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;


@Data
@NoArgsConstructor
@AllArgsConstructor
@QueryResult
public class ProjectAndCardDto {

    private Project project;
    private TimeLog log;
}

package ua.timetracker.reportgenerator.persistence.repository.imperative.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.annotation.QueryResult;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;
import ua.timetracker.shared.persistence.entity.user.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
@QueryResult
public class DevAndCardDto {

    private User user;
    private TimeLog log;
}

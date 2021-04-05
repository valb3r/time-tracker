package ua.timetracker.reportgenerator.persistence.repository.imperative;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.dto.DevAndCardDto;
import ua.timetracker.reportgenerator.persistence.repository.imperative.dto.ProjectAndCardDto;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;

import java.time.LocalDateTime;
import java.util.List;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.LOGGED_FOR;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNER;

@Repository
public interface TimeLogsRepository extends Neo4jRepository<TimeLog, Long> {

    @Query("MATCH (d:User)<-[" + OWNER + "]-(l:TimeLog)-[:" + LOGGED_FOR + "]->(p:Project) " +
            "WHERE id(p) = $projectId AND l.timestamp >= $fromDate AND l.timestamp <= $toDate  " +
            "RETURN d AS user, l as log ORDER BY d.name ASC, l.timestamp ASC")
    List<DevAndCardDto> allLogsForProject(
        @Param("projectId") long projectId, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("MATCH (d:User)<-[" + OWNER + "]-(l:TimeLog)-[:" + LOGGED_FOR + "]->(p:Project) " +
        "WHERE id(d) = $userId AND l.timestamp >= $fromDate AND l.timestamp <= $toDate  " +
        "RETURN p AS project, l as log ORDER BY p.name ASC, l.timestamp ASC")
    List<ProjectAndCardDto> allLogsForUser(
        @Param("userId") long userId, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);
}

package ua.timetracker.reportgenerator.persistence.repository.imperative;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.timetracker.reportgenerator.persistence.repository.imperative.dto.DevAndCardDto;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;

import java.util.List;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.LOGGED_FOR;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNER;

@Repository
public interface TimeLogsRepository extends CrudRepository<TimeLog, Long> {

    @Query("MATCH (d:User)<-[" + OWNER + "]-(l:TimeLog)-[:" + LOGGED_FOR + "]->(p:Project) " +
            "WHERE id(p) = $projectId AND l.timestamp >= localdatetime($fromDate) AND l.timestamp <= localdatetime($toDate)  " +
            "RETURN d AS user, l as log ORDER BY d.name ASC, l.timestamp ASC")
    List<DevAndCardDto> allLogsForProject(
            @Param("projectId") long projectId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);
}

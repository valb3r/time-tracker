package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;

import java.time.LocalDateTime;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNER;

public interface TimeLogsRepository extends ReactiveCrudRepository<TimeLog, Long> {

    @Query(
        "MATCH (t:TimeLog)-[:" + OWNER + "]->(o:User) WHERE id(o) = $userId AND t.loggedFor >= $from AND t.loggedFor <= $to RETURN t"
    )
    Flux<TimeLog> listUploadedCards(
        @Param("userId") long userId,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );
}

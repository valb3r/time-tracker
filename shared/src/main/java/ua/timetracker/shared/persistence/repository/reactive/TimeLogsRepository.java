package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;

import java.time.LocalDateTime;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNER;

@Repository
public interface TimeLogsRepository extends ReactiveCrudRepository<TimeLog, Long> {

    @Query(
        "MATCH (t:TimeLog)-[:" + OWNER + "]->(o:User) WHERE id(o) = $userId AND t.timestamp >= $from AND t.timestamp <= $to RETURN id(t)"
    )
    Flux<Long> listUploadedCards(
        @Param("userId") long userId,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );

    // Note that it is lazy, so TimeLog will not be completely initialized
    @Query("MATCH (t:TimeLog)-[:" + OWNER + "]->(o:User) WHERE id(o) = $userId AND id(t) = $id RETURN t")
    Mono<TimeLog> findByIdAndUserId(@Param("id") long id, @Param("userId") long userId);
}

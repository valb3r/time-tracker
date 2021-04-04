package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLogImage;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNER;

@Repository
public interface TimeLogImagesRepository extends ReactiveCrudRepository<TimeLogImage, Long> {

    // Note that it is lazy, so TimeLog will not be completely initialized
    @Query("MATCH (i:TimeLogImage)-[:" + OWNER + "]->(t:TimeLog)-[:" + OWNER + "]->(o:User) WHERE id(o) = $userId AND id(t) = $cardId AND id(i) = $id RETURN i")
    Mono<TimeLogImage> findByCardIdAndUserIdAndId(@Param("cardId") long cardId, @Param("userId") long userId, @Param("id") long id);

    // Note that it is lazy, so TimeLog will not be completely initialized
    @Query("MATCH (i:TimeLogImage)-[:" + OWNER + "]->(t:TimeLog)-[:" + OWNER + "]->(o:User) WHERE id(o) = $userId AND id(t) = $cardId RETURN i")
    Flux<TimeLogImage> findByCardIdAndUserId(@Param("cardId") long cardId, @Param("userId") long userId);
}

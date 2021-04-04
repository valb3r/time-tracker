package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLogImage;

import java.util.Collection;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.*;

@Repository
public interface TimeLogImagesRepository extends ReactiveCrudRepository<TimeLogImage, Long> {

    // Note that it is lazy, so TimeLog will not be completely initialized
    @Query("MATCH (i:TimeLogImage)-[:" + LOGGED_FOR + "]->(t:TimeLog)-[:" + OWNER + "]->(o:User) WHERE id(o) = $userId AND id(t) = $cardId AND id(i) = $id RETURN i")
    Mono<TimeLogImage> findByCardIdAndUserIdAndId(@Param("cardId") long cardId, @Param("userId") long userId, @Param("id") long id);

    // Note that it is lazy, so TimeLog will not be completely initialized
    @Query("MATCH (i:TimeLogImage)-[:" + LOGGED_FOR + "]->(t:TimeLog)-[:" + OWNER + "]->(o:User) WHERE id(o) = $userId AND id(t) = $cardId RETURN i")
    Flux<TimeLogImage> findByCardIdAndUserId(@Param("cardId") long cardId, @Param("userId") long userId);

    // Note that it is lazy, so TimeLog will not be completely initialized
    @Query("MATCH (i:TimeLogImage)-[:" + LOGGED_FOR + "]->(t:TimeLog)-[:" + LOGGED_FOR + "]->(p:Project) WHERE id(t) IN $cardIds AND id(p) IN $projectIds RETURN i ORDER BY t.timestamp DESC")
    Flux<TimeLogImage> findByCardIdsForProjects(@Param("projectIds") Collection<Long> projectIds, @Param("cardIds") Collection<Long> cardIds);
}

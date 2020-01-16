package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.report.Report;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNS;

@Repository
public interface ReportsRepository extends ReactiveCrudRepository<Report, Long> {

    @Query("MATCH (o)-[:" + OWNS + "]->(r:Report) WHERE id(o) = $ownerId RETURN r ORDER BY r.createdAt")
    Flux<Report> findByOwnerIdOrderByCreatedAtDesc(@Param("ownerId") long ownerId);

    // Impossible to use named query for some reason
    @Query("MATCH (o)-[:" + OWNS + "]->(r:Report) WHERE id(o) = $ownerId AND id(r) = $reportId RETURN r")
    Mono<Report> findByIdAndOwnerId(@Param("reportId") long id, @Param("ownerId") long ownerId);
}

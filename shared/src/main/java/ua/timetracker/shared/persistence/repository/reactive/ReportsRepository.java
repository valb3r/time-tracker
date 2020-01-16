package ua.timetracker.shared.persistence.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.report.Report;

@Repository
public interface ReportsRepository extends ReactiveCrudRepository<Report, Long> {

    Flux<Report> findAllByOwnerId(long ownerId);
    Mono<Report> findByIdAndOwnerId(long id, long ownerId);
}

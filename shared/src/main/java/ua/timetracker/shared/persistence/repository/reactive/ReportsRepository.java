package ua.timetracker.shared.persistence.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ua.timetracker.shared.persistence.entity.report.Report;

@Repository
public interface ReportsRepository extends ReactiveCrudRepository<Report, Long> {
}

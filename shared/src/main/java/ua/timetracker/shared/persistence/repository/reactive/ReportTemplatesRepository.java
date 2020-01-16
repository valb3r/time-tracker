package ua.timetracker.shared.persistence.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ua.timetracker.shared.persistence.entity.report.ReportTemplate;

@Repository
public interface ReportTemplatesRepository extends ReactiveCrudRepository<ReportTemplate, Long> {
}

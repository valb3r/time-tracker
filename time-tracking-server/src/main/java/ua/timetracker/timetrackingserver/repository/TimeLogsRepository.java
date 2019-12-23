package ua.timetracker.timetrackingserver.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.timetracker.shared.persistence.entity.TimeLog;

public interface TimeLogsRepository extends ReactiveCrudRepository<TimeLog, Long> {
}
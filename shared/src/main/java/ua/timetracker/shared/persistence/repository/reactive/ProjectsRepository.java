package ua.timetracker.shared.persistence.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.timetracker.shared.persistence.entity.Project;

public interface ProjectsRepository extends ReactiveCrudRepository<Project, Long> {
}
package ua.timetracker.timetrackingserver.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.timetracker.shared.persistence.entity.Project;


public interface ProjectsRepository extends ReactiveCrudRepository<Project, Long> {
}
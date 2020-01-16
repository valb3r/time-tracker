package ua.timetracker.reportgenerator.persistence.repository.imperative;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.timetracker.shared.persistence.entity.projects.Project;

@Repository
public interface ProjectsRepository extends CrudRepository<Project, Long> {
}

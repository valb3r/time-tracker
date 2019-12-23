package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.Project;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.restapi.dto.project.ProjectCreate;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class ProjectManager {

    private final ProjectsRepository projects;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ProjectDto> createProject(ProjectCreate projectToCreate) {
        return projects.save(Project.MAP.map(projectToCreate)).map(ProjectDto.MAP::map);
    }
}

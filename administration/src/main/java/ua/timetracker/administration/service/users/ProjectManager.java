package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.Project;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.restapi.dto.project.ProjectCreateOrUpdate;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class ProjectManager {

    private final GroupsRepository groups;
    private final ProjectsRepository projects;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ProjectDto> createProject(long parentGroupId, ProjectCreateOrUpdate projectToCreate) {
        val group = groups.findById(parentGroupId);
        val newProject = projects.save(Project.MAP.map(projectToCreate));

        return newProject
            .zipWith(group)
            .flatMap(projectAndGroup -> {
                projectAndGroup.getT2().getProjectsAndInitialize().add(projectAndGroup.getT1());
                return groups.save(projectAndGroup.getT2());
            })
            .switchIfEmpty(Mono.error(new IllegalArgumentException("No group: " + parentGroupId)))
            .flatMap(updatedGroup -> newProject
            .map(ProjectDto.MAP::map));
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ProjectDto> updateProject(long id, ProjectCreateOrUpdate update) {
        return projects.findById(id)
            .map(it -> {
                Project.UPDATE.update(update, it);
                return it;
            })
            .flatMap(projects::save)
            .map(ProjectDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> deleteProject(long projectId) {
        return projects.deleteById(projectId);
    }
}

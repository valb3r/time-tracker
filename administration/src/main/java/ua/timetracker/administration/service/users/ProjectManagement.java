package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.Project;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;

import java.util.List;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.persistence.entity.Relationships.DEVELOPER;
import static ua.timetracker.shared.persistence.entity.Relationships.MANAGER;


@Service
@RequiredArgsConstructor
public class ProjectManagement {

    private final ProjectsRepository projects;
    private final UsersRepository users;
    private final GroupsRepository groups;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ProjectDto> addUserRoles(String role, List<Long> userIds, long projectId) {
        val targetProject = projects.findById(projectId);
        val usersToAdd = users.findAllById(userIds);

        return addActors(role, targetProject, usersToAdd);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ProjectDto> addGroupRoles(String role, List<Long> groupIds, long projectId) {
        val targetProject = projects.findById(projectId);
        val groupsToAdd = groups.findAllById(groupIds);

        return addActors(role, targetProject, groupsToAdd);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ProjectDto> removeUserRoles(String role, List<Long> userIds, long projectId) {
        val targetProject = projects.findById(projectId);
        val usersToAdd = users.findAllById(userIds);

        return removeActors(role, targetProject, usersToAdd);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ProjectDto> removeGroupRoles(String role, List<Long> groupIds, long projectId) {
        val targetProject = projects.findById(projectId);
        val groupsToAdd = groups.findAllById(groupIds);

        return removeActors(role, targetProject, groupsToAdd);
    }

    private Mono<ProjectDto> removeActors(String role, Mono<Project> targetProject, Flux<?> toAdd) {
        if (DEVELOPER.equalsIgnoreCase(role)) {
            return toAdd.zipWith(targetProject, (user, project) -> {
                project.getDevelopers().remove(user);
                return project;
            }).last().flatMap(projects::save).map(ProjectDto.MAP::map);
        }

        if (MANAGER.equalsIgnoreCase(role)) {
            return toAdd.zipWith(targetProject, (user, project) -> {
                project.getManagers().remove(user);
                return project;
            }).last().flatMap(projects::save).map(ProjectDto.MAP::map);
        }

        throw new IllegalStateException("Unknown role " + role);
    }

    private Mono<ProjectDto> addActors(String role, Mono<Project> targetProject, Flux<?> toAdd) {
        if (DEVELOPER.equalsIgnoreCase(role)) {
            return toAdd.zipWith(targetProject, (user, project) -> {
                project.getDevelopers().add(user);
                return project;
            }).last().flatMap(projects::save).map(ProjectDto.MAP::map);
        }

        if (MANAGER.equalsIgnoreCase(role)) {
            return toAdd.zipWith(targetProject, (user, project) -> {
                project.getManagers().add(user);
                return project;
            }).last().flatMap(projects::save).map(ProjectDto.MAP::map);
        }

        throw new IllegalStateException("Unknown role " + role);
    }
}

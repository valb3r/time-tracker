package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.realationships.ProjectRole;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import java.util.Set;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.DEVELOPER;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.MANAGER;


@Service
@RequiredArgsConstructor
public class RoleManager {

    private final ProjectsRepository projects;
    private final GroupsRepository groups;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> addRoles(ProjectRole role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds) {
        if (DEVELOPER == role) {
            return groups.addDevs(projectOrGroupIds, userOrGroupIds);
        }

        if (MANAGER == role) {
            return groups.addManagers(projectOrGroupIds, userOrGroupIds);
        }

        throw new IllegalStateException("Unknown role: " + role);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> removeRoles(ProjectRole role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds) {
        if (DEVELOPER == role) {
            return groups.removeDevs(projectOrGroupIds, userOrGroupIds);
        }

        if (MANAGER == role) {
            return groups.removeManagers(projectOrGroupIds, userOrGroupIds);
        }

        throw new IllegalStateException("Unknown role: " + role);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Flux<UserDto> projectActors(long projectId) {
        return projects.actorsOnProjects(projectId)
            .map(UserDto.MAP::map);
    }
}

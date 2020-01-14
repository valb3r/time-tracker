package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.neo4j.driver.internal.value.ListValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.groups.Group;
import ua.timetracker.shared.persistence.entity.realationships.ProjectRole;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.group.GroupDto;
import ua.timetracker.shared.restapi.dto.project.ProjectActorDto;
import ua.timetracker.shared.restapi.dto.role.RoleDetailsDto;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import java.util.Set;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.DEVELOPER;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.MANAGER;


@Service
@RequiredArgsConstructor
public class RoleManager {

    private final UsersRepository users;
    private final ProjectsRepository projects;
    private final GroupsRepository groups;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> addRoles(ProjectRole role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds,
                               RoleDetailsDto details) {
        if (DEVELOPER == role) {
            return groups.addDevs(
                projectOrGroupIds,
                userOrGroupIds,
                details.getFrom(),
                details.getTo(),
                details.getRate()
            );
        }

        if (MANAGER == role) {
            return groups.addManagers(
                projectOrGroupIds,
                userOrGroupIds,
                details.getFrom(),
                details.getTo(),
                details.getRate()
            );
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
    public Flux<ProjectActorDto> projectActors(long projectId) {
        return projects.actorsOnProjects(projectId)
            .flatMap(this::map);
    }

    private Mono<ProjectActorDto> map(ListValue value) {
        val user = users.findById(value.get(0).asLong());
        Mono<Group> group = Mono.empty();

        // group if present should come exactly before project
        if (value.size() > 2) {
            group = groups.findById(value.get(value.size() - 2).asLong());
        }

        return user
            .zipWith(group)
            .map(it -> new ProjectActorDto(UserDto.MAP.map(it.getT1()), GroupDto.MAP.map(it.getT2())))
            .switchIfEmpty(Mono.defer(() -> user.map(it -> new ProjectActorDto(UserDto.MAP.map(it), null))));
    }
}

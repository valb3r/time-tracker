package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.neo4j.driver.internal.value.MapValue;
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
import ua.timetracker.shared.restapi.dto.project.ProjectDto;
import ua.timetracker.shared.restapi.dto.role.RoleDetailsDto;
import ua.timetracker.shared.restapi.dto.role.RoleDetailsWithType;
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
    public Mono<ProjectDto> updateRole(long roleId, RoleDetailsDto details) {
        return projects
            .updateRoleOnProjectAttributes(roleId, details.getRate(), details.getFrom(), details.getTo())
            .map(ProjectDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<RoleDetailsDto> roleDetails(long roleId) {
        return projects
            .roleDetails(roleId)
            .map(it -> new RoleDetailsWithType(
                it.get("rate").asString(),
                it.get("from").asLocalDateTime(),
                it.get("to").asLocalDateTime(),
                it.get("type").asString()
            ));
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Flux<ProjectActorDto> projectActors(long projectId) {
        return projects.actorsOnProjectsIncludingPastAndFuture(projectId)
            .flatMap(this::map);
    }

    private Mono<ProjectActorDto> map(MapValue nodesAndPath) {
        val nodes = nodesAndPath.get("n");
        val relationships = nodesAndPath.get("r");

        val user = users.findById(nodes.get(0).asLong());
        Mono<Group> group = Mono.empty();

        // group if present should come exactly before project
        if (nodes.size() > 2) {
            group = groups.findById(nodes.get(nodes.size() - 2).asLong());
        }

        return user
            .zipWith(group)
            .map(it -> new ProjectActorDto(
                relationships.get(relationships.size() - 1).asLong(), // last relationship
                UserDto.MAP.map(it.getT1()),
                GroupDto.MAP.map(it.getT2()))
            )
            .switchIfEmpty(Mono.defer(() -> user.map(it -> new ProjectActorDto(
                relationships.get(relationships.size() - 1).asLong(),
                UserDto.MAP.map(it),
                null
            ))));
    }
}

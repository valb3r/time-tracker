package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.realationships.ProjectRole;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;

import java.util.Set;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.DEVELOPER;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.MANAGER;


@Service
@RequiredArgsConstructor
public class RoleManager {

    private final GroupsRepository projects;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> addRoles(ProjectRole role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds) {
        if (DEVELOPER == role) {
            return projects.addDevs(projectOrGroupIds, userOrGroupIds);
        }

        if (MANAGER == role) {
            return projects.addManagers(projectOrGroupIds, userOrGroupIds);
        }

        throw new IllegalStateException("Unknown role: " + role);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> removeRoles(ProjectRole role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds) {
        if (DEVELOPER == role) {
            return projects.removeDevs(projectOrGroupIds, userOrGroupIds);
        }

        if (MANAGER == role) {
            return projects.removeManagers(projectOrGroupIds, userOrGroupIds);
        }

        throw new IllegalStateException("Unknown role: " + role);
    }
}

package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.repository.reactive.RolesRepository;
import ua.timetracker.shared.restapi.dto.role.RoleCreate;
import ua.timetracker.shared.restapi.dto.role.RoleDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class RoleManager {

    private final RolesRepository roles;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<RoleDto> createRole(RoleCreate groupToCreate) {
        return null;
    }
}

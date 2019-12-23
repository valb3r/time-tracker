package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.users.RoleManager;
import ua.timetracker.shared.restapi.dto.role.RoleCreate;
import ua.timetracker.shared.restapi.dto.role.RoleDto;

import javax.validation.Valid;

import static ua.timetracker.shared.restapi.Paths.V1_ROLES;

@RestController
@RequestMapping(value = V1_ROLES,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class RoleController {

    private final RoleManager manager;

    @PutMapping
    public Mono<RoleDto> createRole(@Valid @RequestBody RoleCreate roleToCreate) {
        return manager.createRole(roleToCreate);
    }
}

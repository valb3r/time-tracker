package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.users.UserManager;
import ua.timetracker.shared.restapi.dto.user.UserCreate;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import javax.validation.Valid;

import static ua.timetracker.shared.restapi.Paths.V1_USERS;

@RestController
@RequestMapping(value = V1_USERS,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UserController {

    private final UserManager manager;

    @PutMapping
    @PreAuthorize("#{auth.canCreateUsers()}")
    public Mono<UserDto> createUser(@Valid @RequestBody UserCreate userToCreate) {
        return manager.createUser(userToCreate);
    }
}

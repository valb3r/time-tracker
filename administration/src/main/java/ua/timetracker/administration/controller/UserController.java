package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.securityaspect.ManagedResourceId;
import ua.timetracker.administration.service.securityaspect.OnlyResourceManagers;
import ua.timetracker.administration.service.users.UserManager;
import ua.timetracker.shared.restapi.dto.user.UserCreateDto;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ua.timetracker.shared.restapi.Paths.V1_USERS;

@RestController
@RequestMapping(value = V1_USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserManager manager;

    @OnlyResourceManagers
    @PutMapping(path = "/of_group/{parent_group_id}", consumes = APPLICATION_JSON_VALUE)
    public Mono<UserDto> createUser(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("parent_group_id") long parentGroupId,
        @Valid @RequestBody UserCreateDto userToCreate
    ) {
        return manager.createUser(parentGroupId, userToCreate);
    }

    @OnlyResourceManagers
    @DeleteMapping(path = "/{id}")
    public Mono<Void> deleteUser(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("id") long userToDelete
    ) {
        return manager.deleteUser(userToDelete);
    }
}

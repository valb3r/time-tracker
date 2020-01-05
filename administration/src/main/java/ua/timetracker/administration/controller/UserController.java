package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.users.UserManager;
import ua.timetracker.shared.restapi.dto.user.UserCreate;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ua.timetracker.shared.restapi.Paths.V1_USERS;

@RestController
@RequestMapping(value = V1_USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserManager manager;

    @PutMapping(path = "/of_group/{parent_group_id}", consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("#{auth.canCreateUsers()}")
    public Mono<UserDto> createUser(
        @PathVariable("parent_group_id") long parentGroupId,
        @Valid @RequestBody UserCreate userToCreate
    ) {
        return manager.createUser(parentGroupId, userToCreate);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("#{auth.canCreateUsers()}")
    public Mono<Void> deleteUser(@PathVariable("id") long userToDelete) {
        return manager.deleteUser(userToDelete);
    }
}

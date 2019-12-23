package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.users.GroupManager;
import ua.timetracker.shared.restapi.dto.group.GroupCreate;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import javax.validation.Valid;

import static ua.timetracker.shared.restapi.Paths.V1_GROUPS;

@RestController
@RequestMapping(value = V1_GROUPS,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class GroupController {

    private final GroupManager manager;

    @PutMapping
    public Mono<GroupDto> createGroup(@Valid @RequestBody GroupCreate groupToCreate) {
        return manager.createGroup(groupToCreate);
    }
}

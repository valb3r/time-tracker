package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.users.GroupManager;
import ua.timetracker.shared.restapi.dto.group.GroupCreate;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ua.timetracker.shared.restapi.Paths.V1_GROUPS;

@RestController
@RequestMapping(value = V1_GROUPS)
@RequiredArgsConstructor
public class GroupController {

    private final GroupManager manager;

    @PutMapping(path = "/{parent_group_id}/children", consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("#{auth.canCreateGroups()}")
    public Mono<GroupDto> createGroup(
        @PathVariable("parent_group_id") long parentGroupId,
        @Valid @RequestBody GroupCreate groupToCreate
    ) {
        return manager.createGroup(parentGroupId, groupToCreate);
    }

    @GetMapping
    @PreAuthorize("#{auth.canCreateGroups()}")
    public Flux<GroupDto> listGroups() {
        return manager.groups();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("#{auth.canCreateGroups()}")
    public Mono<GroupDto> groupById(@PathVariable("id") long groupId) {
        return manager.groupById(groupId);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("#{auth.canAssignUsersToGroups()}")
    public Mono<Void> deleteGroup(@PathVariable("id") long groupToDelete) {
        return manager.deleteGroup(groupToDelete);
    }
}

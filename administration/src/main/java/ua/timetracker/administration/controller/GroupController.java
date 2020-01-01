package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.users.GroupManager;
import ua.timetracker.shared.restapi.dto.group.GroupCreate;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @PreAuthorize("#{auth.canCreateGroups()}")
    public Mono<GroupDto> createGroup(@Valid @RequestBody GroupCreate groupToCreate) {
        return manager.createGroup(groupToCreate);
    }

    @PostMapping("/{group_id}/users/{users_ids}")
    @PreAuthorize("#{auth.canAssignUsersToGroups()}")
    public Mono<GroupDto> updateUsersInGroupMembers(
        @Valid @PathVariable("users_ids") List<@NotNull Long> userIds,
        @PathVariable("group_id") long groupId) {
        return manager.updateUsersInGroup(userIds, groupId);
    }

    @PostMapping("/{group_id}/groups/{group_ids}")
    @PreAuthorize("#{auth.canAssignUsersToGroups()}")
    public Mono<GroupDto> updateGroupInGroupMembers(
        @Valid @PathVariable("group_ids") List<@NotNull Long> groupIds,
        @PathVariable("group_id") long groupId) {
        return manager.updateGroupsInGroup(groupIds, groupId);
    }
}

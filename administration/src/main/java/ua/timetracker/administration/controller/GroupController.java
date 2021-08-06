package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.securityaspect.OnlyResourceManagers;
import ua.timetracker.administration.service.securityaspect.ManagedResourceId;
import ua.timetracker.administration.service.users.GroupManager;
import ua.timetracker.shared.restapi.dto.group.GroupCreateOrUpdateDto;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ua.timetracker.shared.restapi.Paths.V1_GROUPS;

@RestController
@RequestMapping(value = V1_GROUPS)
@RequiredArgsConstructor
public class GroupController {

    private final GroupManager manager;

    @OnlyResourceManagers
    @PutMapping(path = "/{parent_group_id}/children", consumes = APPLICATION_JSON_VALUE)
    public Mono<GroupDto> createGroup(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("parent_group_id") long parentGroupId,
        @Valid @RequestBody GroupCreateOrUpdateDto groupToCreate
    ) {
        return manager.createGroup(parentGroupId, groupToCreate);
    }

    @OnlyResourceManagers
    @GetMapping(path = "/{id}")
    public Mono<GroupDto> groupById(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("id") long groupId
    ) {
        return manager.groupById(groupId);
    }

    @OnlyResourceManagers
    @PostMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public Mono<GroupDto> updateGroup(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("id") long groupId,
        @Valid @RequestBody GroupCreateOrUpdateDto groupToUpdate
    ) {
        return manager.updateGroup(groupId, groupToUpdate);
    }

    @OnlyResourceManagers
    @DeleteMapping(path = "/{id}")
    public Mono<Long> deleteGroup(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("id") long groupToDelete) {
        return manager.deleteGroup(groupToDelete);
    }

    @OnlyResourceManagers
    @PostMapping(path = "/{owner_group_ids}/children/users-and-groups/{children_ids}")
    public Mono<Long> addUserOrGroup(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("owner_group_ids") @Valid Set<@NotNull Long> ownerIds,
        @ManagedResourceId @PathVariable("children_ids") @Valid Set<@NotNull Long> childrenIds
    ) {
        return manager.addUserOrGroupToGroup(childrenIds, ownerIds);
    }

    @OnlyResourceManagers
    @DeleteMapping(path = "/{owner_group_ids}/children/users-and-groups/{children_ids}")
    public Mono<Long> removeUserOrGroup(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("owner_group_ids") @Valid Set<@NotNull Long> ownerIds,
        @ManagedResourceId @PathVariable("children_ids") @Valid Set<@NotNull Long> childrenIds
    ) {
        return manager.removeUserOrGroupToGroup(childrenIds, ownerIds);
    }

    @OnlyResourceManagers
    @PostMapping(path = "/{owner_group_ids}/children/projects/{children_ids}")
    public Mono<Long> addProject(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("owner_group_ids") @Valid Set<@NotNull Long> ownerIds,
        @ManagedResourceId @PathVariable("children_ids") @Valid Set<@NotNull Long> projectIds
    ) {
        return manager.addProjectsToGroup(projectIds, ownerIds);
    }

    @OnlyResourceManagers
    @DeleteMapping(path = "/{owner_group_ids}/children/projects/{children_ids}")
    public Mono<Long> removeProject(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("owner_group_ids") @Valid Set<@NotNull Long> ownerIds,
        @ManagedResourceId @PathVariable("children_ids") @Valid Set<@NotNull Long> projectIds
    ) {
        return manager.removeProjectsFromGroup(projectIds, ownerIds);
    }
}

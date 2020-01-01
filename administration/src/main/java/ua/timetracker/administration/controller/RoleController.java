package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.users.ProjectManagement;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static ua.timetracker.shared.restapi.Paths.V1_ROLES;

@RestController
@RequestMapping(value = V1_ROLES,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class RoleController {

    private final ProjectManagement manager;

    @PreAuthorize("#{auth.canAssignRoles()}")
    @PostMapping("/{role}/users/{user_ids}/in/{project_id}")
    public Mono<ProjectDto> assignUserRoles(
        @Valid @NotBlank @PathVariable("role") String role,
        @Valid @PathVariable("user_ids") List<@NotNull Long> userIds,
        @PathVariable("project_id") long projectId
    ) {
        return manager.addUserRoles(role, userIds, projectId);
    }

    @PreAuthorize("#{auth.canAssignRoles()}")
    @PostMapping("/{role}/groups/{group_ids}/in/{project_id}")
    public Mono<ProjectDto> assignGroupRoles(
        @Valid @NotBlank @PathVariable("role") String role,
        @Valid @PathVariable("group_ids") List<@NotNull Long> groupIds,
        @PathVariable("project_id") long projectId
    ) {
        return manager.addGroupRoles(role, groupIds, projectId);
    }

    @PreAuthorize("#{auth.canAssignRoles()}")
    @DeleteMapping("/{role}/users/{user_ids}/in/{project_id}")
    public Mono<ProjectDto> removeUserRoles(
        @Valid @NotBlank @PathVariable("role") String role,
        @Valid @PathVariable("user_ids") List<@NotNull Long> userIds,
        @PathVariable("project_id") long projectId
    ) {
        return manager.removeUserRoles(role, userIds, projectId);
    }

    @PreAuthorize("#{auth.canAssignRoles()}")
    @DeleteMapping("/{role}/groups/{group_ids}/in/{project_id}")
    public Mono<ProjectDto> removeGroupRoles(
        @Valid @NotBlank @PathVariable("role") String role,
        @Valid @PathVariable("group_ids") List<@NotNull Long> groupIds,
        @PathVariable("project_id") long projectId
    ) {
        return manager.removeGroupRoles(role, groupIds, projectId);
    }
}

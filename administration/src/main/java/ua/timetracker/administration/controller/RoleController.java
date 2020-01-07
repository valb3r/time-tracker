package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.securityaspect.CanManageResource;
import ua.timetracker.administration.service.securityaspect.ManagedResourceId;
import ua.timetracker.administration.service.users.RoleManager;
import ua.timetracker.shared.persistence.entity.realationships.ProjectRole;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ua.timetracker.shared.restapi.Paths.V1_ROLES;

@RestController
@RequestMapping(value = V1_ROLES)
@RequiredArgsConstructor
public class RoleController {

    private final RoleManager manager;

    @CanManageResource
    @PostMapping(path = "/{role}/actors/{user_or_group_ids}/in/{project_or_group_ids}")
    public Mono<Long> assignUserRoles(
        @Parameter(hidden = true) Authentication user,
        @Valid @NotBlank @PathVariable("role") ProjectRole role,
        @ManagedResourceId @Valid @PathVariable("user_or_group_ids") Set<@NotNull Long> userOrGroupIds,
        @ManagedResourceId @PathVariable("project_or_group_ids") Set<@NotNull Long> projectOrGroupIds
    ) {
        return manager.addRoles(role, userOrGroupIds, projectOrGroupIds);
    }

    @CanManageResource
    @DeleteMapping(path = "/{role}/actors/{user_or_group_ids}/in/{project_or_group_ids}")
    public Mono<Long> removeGroupRoles(
        @Parameter(hidden = true) Authentication user,
        @Valid @NotBlank @PathVariable("role") ProjectRole role,
        @ManagedResourceId @Valid @PathVariable("user_or_group_ids") Set<@NotNull Long> userOrGroupIds,
        @ManagedResourceId @PathVariable("project_or_group_ids") Set<@NotNull Long> projectOrGroupIds
    ) {
        return manager.removeRoles(role, userOrGroupIds, projectOrGroupIds);
    }
}

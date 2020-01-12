package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ua.timetracker.administration.service.securityaspect.ManagedResourceId;
import ua.timetracker.administration.service.securityaspect.OnlyResourceManagers;
import ua.timetracker.administration.service.users.OwnerResourcesService;
import ua.timetracker.shared.restapi.dto.PathEntry;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import static ua.timetracker.shared.restapi.Paths.V1_OWNED;
import static ua.timetracker.shared.util.UserIdUtil.id;

@RestController
@RequestMapping(value = V1_OWNED)
@RequiredArgsConstructor
public class OwnedResourcesController {

    private final OwnerResourcesService resources;

    @OnlyResourceManagers
    @GetMapping(path = "/{owning_group_or_user_id}")
    public Flux<GroupDto> ownedGroups(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("owning_group_or_user_id") long owningGroupOrUserId
    ) {
        return resources.listOwnedGroups(owningGroupOrUserId);
    }

    // Authentication object is enough
    @GetMapping
    public Flux<PathEntry<GroupDto>> selfOwnedResources(@ManagedResourceId @Parameter(hidden = true) Authentication user) {
        return resources.allOwnedResources(id(user));
    }
}

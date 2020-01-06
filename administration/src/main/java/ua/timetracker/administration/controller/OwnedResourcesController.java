package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ua.timetracker.administration.service.users.OwnerResourcesService;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import static ua.timetracker.shared.restapi.Paths.V1_OWNED;

@RestController
@RequestMapping(value = V1_OWNED)
@RequiredArgsConstructor
public class OwnedResourcesController {

    private final OwnerResourcesService resources;

    @GetMapping(path = "/{owning_group_or_user_id}")
    @PreAuthorize("#{auth.canAssignUsersToGroups()}")
    public Flux<GroupDto> ownedGroups(@PathVariable("owning_group_or_user_id") long owningGroupOrUserId) {
        return resources.listOwnedGroups(owningGroupOrUserId);
    }
}

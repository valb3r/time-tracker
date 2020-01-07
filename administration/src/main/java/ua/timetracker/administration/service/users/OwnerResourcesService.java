package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class OwnerResourcesService {

    private final GroupsRepository groups;

    @Transactional(REACTIVE_TX_MANAGER)
    public Flux<GroupDto> listOwnedGroups(long owningGroupOrUserId) {
        return groups
            .findAllById(groups.ownedGroupIds(owningGroupOrUserId)) // Relationships are not loaded, initializing eagerly
            .map(GroupDto.MAP::map);
    }
}
package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.groups.Group;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.restapi.dto.group.GroupCreate;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class GroupManager {

    private final GroupsRepository groups;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<GroupDto> createGroup(long parentGroupId, GroupCreate groupToCreate) {
        val group = groups.findById(parentGroupId);
        val newGroup = groups.save(Group.MAP.map(groupToCreate));

        return newGroup
            .zipWith(group)
            .flatMap(childrenAndParent -> {
                childrenAndParent.getT2().getChildrenAndInitialize().add(childrenAndParent.getT1());
                return groups.save(childrenAndParent.getT2());
            })
            .switchIfEmpty(Mono.error(new IllegalArgumentException("No group: " + parentGroupId)))
            .flatMap(updatedGroup -> newGroup)
            .map(GroupDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Flux<GroupDto> groups() {
        return groups.findAll().map(GroupDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<GroupDto> groupById(long groupId) {
        return groups.findById(groupId).map(GroupDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> deleteGroup(long groupId) {
        return groups.deleteById(groupId);
    }
}

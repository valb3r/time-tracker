package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.groups.Group;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.group.GroupCreate;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import java.util.List;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class GroupManager {

    private final UsersRepository users;
    private final GroupsRepository groups;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<GroupDto> createGroup(GroupCreate groupToCreate) {
        return groups.save(Group.MAP.map(groupToCreate)).map(GroupDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<GroupDto> updateUsersInGroup(List<Long> userIds, long groupId) {
        val usersToAdd = users.findAllById(userIds);
        Mono<Group> targetGroup = groups.findById(groupId)
            .map(it -> {
                it.getUsers().clear();
                return it;
            });

        return usersToAdd.zipWith(targetGroup, (user, group) -> {
            group.getUsers().add(user);
            return group;
        }).last()
            .flatMap(groups::save)
            .map(GroupDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<GroupDto> updateGroupsInGroup(List<Long> groupIds, long groupId) {
        val groupsToAdd = groups.findAllById(groupIds);
        Mono<Group> targetGroup = groups.findById(groupId)
            .map(it -> {
                it.getChildren().clear();
                return it;
            });

        return groupsToAdd.zipWith(targetGroup, (child, group) -> {
            group.getChildren().add(child);
            return group;
        }).last()
            .flatMap(groups::save)
            .map(GroupDto.MAP::map);
    }
}

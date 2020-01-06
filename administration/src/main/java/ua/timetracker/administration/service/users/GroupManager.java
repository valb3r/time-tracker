package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.groups.Group;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.restapi.dto.group.GroupCreate;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import java.util.Set;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Slf4j
@Service
@RequiredArgsConstructor
public class GroupManager {

    private final GroupsRepository groups;

    @Transactional(value = REACTIVE_TX_MANAGER)
    public Mono<GroupDto> createGroup(long parentGroupId, GroupCreate groupToCreate) {
        return groups.save(Group.MAP.map(groupToCreate))
            .flatMap(newGroup -> groups.mergeToParent(newGroup.getId(), parentGroupId))
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

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> addUserOrGroupToGroup(Set<Long> userOrGroupIds, Set<Long> targetGroupIds) {
        return groups.addUserOrGroupToGroup(userOrGroupIds, targetGroupIds);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> removeUserOrGroupToGroup(Set<Long> userOrGroupIds, Set<Long> targetGroupIds) {
        return groups.removeUserOrGroupFromGroup(userOrGroupIds, targetGroupIds);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> addProjectsToGroup(Set<Long> projectIds, Set<Long> targetGroupIds) {
        return groups.addProjectsToGroup(projectIds, targetGroupIds);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Long> removeProjectsFromGroup(Set<Long> projectIds, Set<Long> targetGroupIds) {
        return groups.removeProjectsFromGroup(projectIds, targetGroupIds);
    }
}

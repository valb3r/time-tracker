package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.neo4j.driver.internal.value.ListValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.restapi.dto.PathEntry;
import ua.timetracker.shared.restapi.dto.group.GroupDto;

import java.util.Objects;

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

    @Transactional(REACTIVE_TX_MANAGER)
    public Flux<PathEntry<GroupDto>> allOwnedResources(long owningUserId) {
        val mapWithPath = groups.ownedGroupWithPaths(owningUserId)
            .map(this::computePathMap);

        val entities = groups.findAllById(mapWithPath.map(PathEntry::getEntry));

        return mapWithPath
            .groupJoin(
                entities,
                e1 -> Flux.never(),
                e2 -> Flux.never(),
                (e1, group) -> group.filter(e2 -> Objects.equals(e1.getEntry(), e2.getId()))
                    .map(it -> new PathEntry<>(e1.getPath(), GroupDto.MAP.map(it)))
            ).flatMap(it -> it);
    }

    private PathEntry<Long> computePathMap(ListValue listOfPairs) {
        String result = "" + listOfPairs.get(0).get(0);
        for (int pairPos = 0; pairPos < listOfPairs.size(); pairPos++) {
            result += "/" + listOfPairs.get(pairPos).get(1);
        }

        return new PathEntry<>(result, listOfPairs.get(listOfPairs.size() - 1).get(1).asLong());
    }
}

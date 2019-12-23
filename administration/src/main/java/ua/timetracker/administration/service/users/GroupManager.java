package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.restapi.dto.user.UserCreate;
import ua.timetracker.shared.restapi.dto.user.UserCreated;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class GroupManager {

    private final GroupsRepository groups;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserCreated> createUser(@RequestBody UserCreate userToCreate) {
        return null;
    }
}

package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.User;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.user.UserCreate;
import ua.timetracker.shared.restapi.dto.user.UserCreated;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class UserManager {

    private final UsersRepository users;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserCreated> createUser(@RequestBody UserCreate userToCreate) {
        return users.save(new User(userToCreate)).map(UserCreated::new);
    }
}

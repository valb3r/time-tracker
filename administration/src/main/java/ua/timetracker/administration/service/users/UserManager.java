package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.user.UserCreate;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class UserManager {

    private final UsersRepository users;
    private final PasswordEncoder encoder;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> createUser(UserCreate userToCreate) {
        val newUser = new User(userToCreate);
        newUser.setEncodedPassword(encoder.encode(userToCreate.getPassword()));
        return users.save(newUser).map(UserDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> findUser(String name) {
        return users.findByName(name).map(UserDto.MAP::map);
    }
}

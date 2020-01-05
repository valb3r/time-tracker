package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.user.UserCreate;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class UserManager {

    private final GroupsRepository groups;
    private final UsersRepository users;
    private final PasswordEncoder encoder;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> createUser(long parentGroupId, UserCreate userToCreate) {
        val group = groups.findById(parentGroupId);
        val toSave = new User(userToCreate);
        toSave.setEncodedPassword(encoder.encode(userToCreate.getPassword()));
        val newUser = users.save(toSave);

        return newUser
            .zipWith(group)
            .flatMap(userAndGroup -> {
                userAndGroup.getT2().getUsers().add(userAndGroup.getT1());
                return groups.save(userAndGroup.getT2());
            })
            .switchIfEmpty(Mono.error(new IllegalArgumentException("No group: " + parentGroupId)))
            .flatMap(updatedGroup -> newUser)
            .map(UserDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> findUser(String name) {
        return users.findByName(name).map(UserDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> deleteUser(long userId) {
        return users.deleteById(userId);
    }
}

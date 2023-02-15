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
import ua.timetracker.shared.restapi.dto.user.PasswordUpdateDto;
import ua.timetracker.shared.restapi.dto.user.UserCreateDto;
import ua.timetracker.shared.restapi.dto.user.UserDto;
import ua.timetracker.shared.restapi.dto.user.UserUpdateDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;


@Service
@RequiredArgsConstructor
public class UserManager {

    private final GroupsRepository groups;
    private final UsersRepository users;
    private final PasswordEncoder encoder;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> createUser(long parentGroupId, UserCreateDto userToCreate) {
        val group = groups.findById(parentGroupId);
        val toSave = User.MAP.map(userToCreate);
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
    public Mono<UserDto> getUser(long userId) {
        return users.findById(userId).map(UserDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> findUser(String name) {
        return users.findByUsername(name).map(UserDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> updateUser(long userId, UserUpdateDto updateDto) {
        return users.findById(userId)
            .flatMap(it -> {
                User.UPDATE.update(updateDto, it);
                return users.save(it);
            }).map(UserDto.MAP::map);

    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> updatePassword(long userId, PasswordUpdateDto passwordUpdate) {
        return users.findById(userId)
            .filter(it -> encoder.matches(passwordUpdate.getCurrent(), it.getEncodedPassword()))
            .flatMap(it -> {
                it.setEncodedPassword(encoder.encode(passwordUpdate.getNewpassword()));
                return users.save(it);
            }).map(UserDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<UserDto> updatePassword(long userId, String password) {
        return users.findById(userId)
                .flatMap(it -> {
                    it.setEncodedPassword(encoder.encode(password));
                    return users.save(it);
                }).map(UserDto.MAP::map);
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> deleteUser(long userId) {
        return users.deleteById(userId);
    }
}

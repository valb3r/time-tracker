package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.restapi.dto.user.UserCreateDto;

import javax.annotation.PostConstruct;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class InitialUserCreation {

    private final GroupsRepository groups;
    private final UserManager users;

    @Value("${demo.reset-admin-password-on-start}")
    private boolean resetAdminPasswordOnStart;

    @Transactional(REACTIVE_TX_MANAGER)
    @PostConstruct
    public void initInitialUser() {
        users.findUser("admin")
            .switchIfEmpty(Mono.defer(
                () -> groups
                    .findByName("Root admins group")
                    .flatMap(admins -> users.createUser(admins.getId(), new UserCreateDto("admin", "super", "admin", "0"))))
            ).flatMap(user -> {
                if (resetAdminPasswordOnStart) {
                    return users.updatePassword(user.getId(), "admin");
                }
                return Mono.just(user);
            }).subscribe();
    }
}

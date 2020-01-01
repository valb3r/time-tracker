package ua.timetracker.administration.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;

import javax.annotation.PostConstruct;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class InitialUserCreation {

    private final UsersRepository users;
    private final PasswordEncoder encoder;

    @Transactional(REACTIVE_TX_MANAGER)
    @PostConstruct
    void initInitialUser() {
        users.findByName("admin")
            .switchIfEmpty(Mono.defer(
                () -> users.save(User.builder().name("admin").encodedPassword(encoder.encode("admin")).build()))
            )
            .subscribe();
    }
}

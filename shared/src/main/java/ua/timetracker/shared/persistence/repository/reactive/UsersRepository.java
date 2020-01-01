package ua.timetracker.shared.persistence.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.user.User;

public interface UsersRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByName(String name);
}

package ua.timetracker.shared.persistence.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.user.User;

@Repository
public interface UsersRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByUsername(String name);
}

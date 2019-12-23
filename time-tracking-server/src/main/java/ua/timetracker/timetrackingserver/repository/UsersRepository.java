package ua.timetracker.timetrackingserver.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.timetracker.shared.persistence.entity.User;

public interface UsersRepository extends ReactiveCrudRepository<User, Long> {
}
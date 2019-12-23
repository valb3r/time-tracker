package ua.timetracker.shared.persistence.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.timetracker.shared.persistence.entity.roles.Role;

public interface RolesRepository extends ReactiveCrudRepository<Role, Long> {
}
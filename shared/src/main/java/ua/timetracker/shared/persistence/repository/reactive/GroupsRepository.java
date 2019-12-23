package ua.timetracker.shared.persistence.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.timetracker.shared.persistence.entity.groups.Group;

public interface GroupsRepository extends ReactiveCrudRepository<Group, Long> {
}
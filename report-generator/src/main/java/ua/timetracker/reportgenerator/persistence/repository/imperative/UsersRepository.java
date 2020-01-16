package ua.timetracker.reportgenerator.persistence.repository.imperative;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.timetracker.shared.persistence.entity.user.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
}

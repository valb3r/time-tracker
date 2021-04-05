package ua.timetracker.reportgenerator.persistence.repository.imperative;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import ua.timetracker.shared.persistence.entity.user.User;

@Repository
public interface UsersRepository extends Neo4jRepository<User, Long> {
}

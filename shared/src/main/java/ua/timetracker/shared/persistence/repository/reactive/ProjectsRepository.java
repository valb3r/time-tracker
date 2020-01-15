package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.driver.internal.value.ListValue;
import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ua.timetracker.shared.persistence.entity.projects.Project;

import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.DEVELOPER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.MANAGER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.HAS_CHILD;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.IN_GROUP;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNS;

@Repository
public interface ProjectsRepository extends ReactiveCrudRepository<Project, Long> {

    /**
     * Derived and related to {@link GroupsRepository#ownedGroupIds(long)}
     */
    @Query(
        "MATCH (m:User)-[:" + IN_GROUP + "]->(g:Group)-[:" + HAS_CHILD + "*]->(r:Group)-[:OWNS]->(p:Project) WHERE id(m) = $userId RETURN p " +
        "UNION MATCH (m:Group)-[:" + HAS_CHILD + "*]->(r:Group)-[:OWNS]->(p:Project) WHERE id(m) = $userId RETURN p " +
        "UNION MATCH (m)-[role:" + MANAGER_ROLE + "|" + DEVELOPER_ROLE + "|" + IN_GROUP + "|" + OWNS + "*]->(p:Project) WHERE id(m) = $userId AND (m:Group OR m:User) RETURN p"
    )
    Flux<Project> timeLoggableProjects(@Param("userId") long userId);

    /**
     * Related to {@link ProjectsRepository#timeLoggableProjects(long)}.
     */
    @Query(
        "MATCH path = (m:User)-[:" + MANAGER_ROLE + "|" + DEVELOPER_ROLE + "|" + IN_GROUP + "|" + OWNS + "*]->(p:Project) WHERE id(p) = $projectId " +
        "RETURN [node IN nodes(path) | id(node)]")
    Flux<ListValue> actorsOnProjects(@Param("projectId") long projectId);
}

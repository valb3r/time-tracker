package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.driver.internal.value.MapValue;
import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.Project;

import java.time.LocalDateTime;

import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.DEVELOPER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.MANAGER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.HAS_CHILD;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.IN_GROUP;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNS;
import static ua.timetracker.shared.persistence.repository.reactive.QueryConst.DATE_VALID_FILTER_ON_DEV_OR_MGR_ROLE;

@Repository
public interface ProjectsRepository extends ReactiveCrudRepository<Project, Long> {

    /**
     * Derived and related to {@link GroupsRepository#ownedGroupIds(long)}
     */
    @Query(
        "MATCH (m:User)-[:" + IN_GROUP + "]->(g:Group)-[:" + HAS_CHILD + "*]->(r:Group)-[:OWNS]->(p:Project) WHERE id(m) = $userId RETURN p " +
        "UNION MATCH (m:Group)-[:" + HAS_CHILD + "*]->(r:Group)-[:OWNS]->(p:Project) WHERE id(m) = $userId RETURN p " +
        "UNION MATCH (m)-[role:" + MANAGER_ROLE + "|" + DEVELOPER_ROLE + "|" + IN_GROUP + "|" + OWNS + "*]->(p:Project) WHERE id(m) = $userId AND (m:Group OR m:User) " +
            DATE_VALID_FILTER_ON_DEV_OR_MGR_ROLE +
            "RETURN p"
    )
    Flux<Project> timeLoggableProjects(@Param("userId") long userId);

    /**
     * Related to {@link ProjectsRepository#timeLoggableProjects(long)}.
     * No time validity filter as it is 'show actors query'
     */
    @Query(
        "MATCH path = (m:User)-[role:" + MANAGER_ROLE + "|" + DEVELOPER_ROLE + "|" + IN_GROUP + "|" + OWNS + "*]->(p:Project) WHERE id(p) = $projectId " +
            "RETURN {n: [node IN nodes(path) | id(node)], r: [rel IN relationships(path) | id(rel)]}")
    Flux<MapValue> actorsOnProjectsIncludingPastAndFuture(@Param("projectId") long projectId);

    @Query("MATCH ()-[r:" + MANAGER_ROLE + "|" + DEVELOPER_ROLE +"]->(p:Project) WHERE id(r) = $roleId " +
           "SET r.rate = $rate, r.from = $from, r.to = $to RETURN p")
    Mono<Project> updateRoleOnProjectAttributes(
        @Param("roleId") long roleId,
        @Param("rate") String rate,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to);


    @Query("MATCH ()-[r]->(p:Project) WHERE id(r) = $roleId RETURN p")
    Mono<Project> findByRoleId(@Param("roleId") long roleId);

    @Query("MATCH ()-[r]->(p:Project) WHERE id(r) = $roleId RETURN {rate: r.rate, from: r.from, to: r.to, type: type(r)}")
    Mono<MapValue> roleDetails(@Param("roleId") long roleId);
}

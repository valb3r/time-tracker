package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.groups.Group;

import java.util.Set;

import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.DEVELOPER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.MANAGER_ROLE;

public interface GroupsRepository extends ReactiveCrudRepository<Group, Long> {

    Mono<Group> findByName(String name);

    @Query("MATCH (r),(d) WHERE id(r) IN $resources AND id(d) IN $developers AND (r:Project OR r:Group) AND (d:User OR d:Group) CREATE (r)<-[:" + DEVELOPER_ROLE +"]-(d) RETURN COUNT(r)")
    Mono<Long> addDevs(@Param("resources") Set<Long> resourceIds, @Param("developers") Set<Long> developers);

    @Query("MATCH (r),(m) WHERE id(r) IN $resources AND id(m) IN $managers AND (r:Project OR r:Group) AND (m:User OR m:Group) CREATE (r)<-[:" + MANAGER_ROLE + "]-(m) RETURN COUNT(r)")
    Mono<Long> addManagers(@Param("resources") Set<Long> resourceIds, @Param("managers") Set<Long> managers);

    @Query("MATCH (r)<-[role:" + DEVELOPER_ROLE + "]-(d) WHERE id(p) IN $resources AND id(d) IN $developers AND (r:Project OR r:Group) AND (d:User OR d:Group) DELETE role RETURN COUNT(role)")
    Mono<Long> removeDevs(@Param("resources") Set<Long> resourceIds, @Param("developers") Set<Long> developers);

    @Query("MATCH (r)<-[role:" + MANAGER_ROLE + "]-(m) WHERE id(p) IN $resources AND id(m) IN $managers AND (r:Project OR r:Group) AND (m:User OR m:Group) DELETE role RETURN COUNT(role)")
    Mono<Long> removeManagers(@Param("resources") Set<Long> resourceIds, @Param("managers") Set<Long> managers);
}

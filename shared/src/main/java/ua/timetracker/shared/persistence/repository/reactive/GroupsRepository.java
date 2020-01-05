package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.groups.Group;

import java.util.Set;

import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.DEVELOPER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.MANAGER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.CHILD;

public interface GroupsRepository extends ReactiveCrudRepository<Group, Long> {

    Mono<Group> findByName(String name);

    @Query("MATCH (r),(d) WHERE id(r) IN $resources AND id(d) IN $developers AND (r:Project OR r:Group) AND (d:User OR d:Group) CREATE (r)<-[:" + DEVELOPER_ROLE +"]-(d) RETURN COUNT(r)")
    Mono<Long> addDevs(@Param("resources") Set<Long> resourceIds, @Param("developers") Set<Long> developers);

    @Query("MATCH (r),(m) WHERE id(r) IN $resources AND id(m) IN $managers AND (r:Project OR r:Group) AND (m:User OR m:Group) CREATE (r)<-[:" + MANAGER_ROLE + "]-(m) RETURN COUNT(r)")
    Mono<Long> addManagers(@Param("resources") Set<Long> resourceIds, @Param("managers") Set<Long> managers);

    @Query("MATCH (m)-[role:" + DEVELOPER_ROLE + "]->(d) WHERE id(p) IN $resources AND id(d) IN $developers AND (r:Project OR r:Group) AND (d:User OR d:Group) DELETE role RETURN COUNT(role)")
    Mono<Long> removeDevs(@Param("resources") Set<Long> resourceIds, @Param("developers") Set<Long> developers);

    @Query("MATCH (m)-[role:" + MANAGER_ROLE + "]->(r) WHERE id(p) IN $resources AND id(m) IN $managers AND (r:Project OR r:Group) AND (m:User OR m:Group) DELETE role RETURN COUNT(role)")
    Mono<Long> removeManagers(@Param("resources") Set<Long> resourceIds, @Param("managers") Set<Long> managers);

    @Query("MATCH (m)-[role:" + MANAGER_ROLE + "|" + CHILD + "*]->(r) WHERE id(m) = $ownerId AND (m:Group OR m:User) RETURN r")
    Flux<Group> ownedResources(@Param("ownerId") long owningUserOrGroupId);

    @Query("MATCH (c:Group),(p:Group) WHERE id(c) = $childId AND id(p) = $parentId MERGE (c)-[:" + CHILD + "]->(p) RETURN c")
    Mono<Group> mergeToParent(@Param("childId") long childId, @Param("parentId") long parentId);
}

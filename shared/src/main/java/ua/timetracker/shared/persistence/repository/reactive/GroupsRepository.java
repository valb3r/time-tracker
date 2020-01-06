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
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.IN_GROUP;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNS;

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

    // Owns children of group user/group belongs to AND direct manager resources
    @Query(
        "MATCH (m:User)-[:" + IN_GROUP + "]->(g:Group)-[:" + CHILD + "*]->(r:Group) WHERE id(m) = $ownerId RETURN id(r) " +
        "UNION MATCH (m:Group)-[:" + CHILD + "*]->(r:Group) WHERE id(m) = $ownerId RETURN id(r) " +
        "UNION MATCH (m)-[role:" + MANAGER_ROLE + "*]->(r:Group) WHERE id(m) = $ownerId AND (m:Group OR m:User) RETURN id(r)")
    Flux<Long> ownedGroupIds(@Param("ownerId") long owningUserOrGroupId);

    @Query("MATCH (c:Group),(p:Group) WHERE id(c) = $childId AND id(p) = $parentId MERGE (p)-[:" + CHILD + "]->(c) RETURN c")
    Mono<Group> mergeToParent(@Param("childId") long childId, @Param("parentId") long parentId);

    @Query("MATCH (u),(g:Group) WHERE id(u) IN $resources AND id(g) IN $groups AND (u:User OR u:Group) CREATE (u)-[:" + IN_GROUP +"]->(g) RETURN COUNT(g)")
    Mono<Long> addUserOrGroupToGroup(@Param("resources") Set<Long> groupOrUserIds, @Param("groups") Set<Long> groupIds);

    @Query("MATCH (u)-[role:" + IN_GROUP + "]->(g:Group) WHERE id(u) IN $resources AND id(g) IN $groups AND (u:User OR u:Group) DELETE role RETURN COUNT(role)")
    Mono<Long> removeUserOrGroupFromGroup(@Param("resources") Set<Long> groupOrUserIds, @Param("groups") Set<Long> groupIds);

    @Query("MATCH (g:Group),(p:Project) WHERE id(p) IN $projects AND id(g) IN $groups CREATE (g)-[:" + OWNS +"]->(p) RETURN COUNT(g)")
    Mono<Long> addProjectsToGroup(@Param("projects") Set<Long> projectIds, @Param("groups") Set<Long> groupIds);

    @Query("MATCH (g:Group)-[role:" + OWNS + "]->(p:Project) WHERE id(p) IN $projects AND id(g) IN $groups DELETE role RETURN COUNT(role)")
    Mono<Long> removeProjectsFromGroup(@Param("projects") Set<Long> projectIds, @Param("groups") Set<Long> groupIds);
}

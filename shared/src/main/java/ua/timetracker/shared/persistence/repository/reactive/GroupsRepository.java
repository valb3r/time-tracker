package ua.timetracker.shared.persistence.repository.reactive;

import org.neo4j.driver.internal.value.ListValue;
import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.groups.Group;

import java.time.LocalDateTime;
import java.util.Collection;

import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.DEVELOPER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.MANAGER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.HAS_CHILD;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.IN_GROUP;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNS;
import static ua.timetracker.shared.persistence.repository.reactive.QueryConst.DATE_VALID_FILTER_ON_DEV_OR_MGR_ROLE;

@Repository
public interface GroupsRepository extends ReactiveCrudRepository<Group, Long> {

    Mono<Group> findByName(String name);

    @Query("MATCH (r),(d) WHERE id(r) IN $resources AND id(d) IN $developers AND (r:Project OR r:Group) AND (d:User OR d:Group) CREATE (r)<-[:" + DEVELOPER_ROLE +" {from: $from, to: $to, rate: $rate}]-(d) RETURN COUNT(r)")
    Mono<Long> addDevs(@Param("resources") Collection<Long> resourceIds, @Param("developers") Collection<Long> developers,
                       @Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("rate") String rate);

    @Query("MATCH (r),(m) WHERE id(r) IN $resources AND id(m) IN $managers AND (r:Project OR r:Group) AND (m:User OR m:Group) CREATE (r)<-[:" + MANAGER_ROLE + " {from: $from, to: $to, rate: $rate}]-(m) RETURN COUNT(r)")
    Mono<Long> addManagers(@Param("resources") Collection<Long> resourceIds, @Param("managers") Collection<Long> managers,
                           @Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("rate") String rate);

    @Query("MATCH (d)-[role:" + DEVELOPER_ROLE + "]->(p) WHERE id(p) IN $resources AND id(d) IN $developers AND (p:Project OR p:Group) AND (d:User OR d:Group) DELETE role RETURN COUNT(role)")
    Mono<Long> removeDevs(@Param("resources") Collection<Long> resourceIds, @Param("developers") Collection<Long> developers);

    @Query("MATCH (m)-[role:" + MANAGER_ROLE + "]->(p) WHERE id(p) IN $resources AND id(m) IN $managers AND (p:Project OR p:Group) AND (m:User OR m:Group) DELETE role RETURN COUNT(role)")
    Mono<Long> removeManagers(@Param("resources") Collection<Long> resourceIds, @Param("managers") Collection<Long> managers);

    /**
     * Derived and related to {@link ProjectsRepository#timeLoggableProjects(long)}
     */
    // Owns children of group user/group belongs to AND direct manager resources
    @Query(
        "MATCH (m:User)-[:" + IN_GROUP + "]->(g:Group)-[:" + HAS_CHILD + "*]->(r:Group) WHERE id(m) = $ownerId RETURN id(r) " +
        "UNION MATCH (m:Group)-[:" + HAS_CHILD + "*]->(r:Group) WHERE id(m) = $ownerId RETURN id(r) " +
        "UNION MATCH (m)-[role:" + MANAGER_ROLE + "*]->(r:Group) WHERE id(m) = $ownerId AND (m:Group OR m:User) " +
            DATE_VALID_FILTER_ON_DEV_OR_MGR_ROLE +
            "RETURN id(r)"
    )
    Flux<Long> ownedGroupIds(@Param("ownerId") long owningUserOrGroupId);

    /**
     * Pattern must be same as {@link GroupsRepository#ownedGroupIds(long)}.
     */
    @Query(
        "MATCH p = (m:User)-[:" + IN_GROUP + "]->(g:Group)-[:" + HAS_CHILD + "*]->(r:Group) WHERE id(m) = $ownerId " +
        "RETURN [rel IN relationships(p) | [id(startNode(rel)), id(endNode(rel))]] " +
        "UNION MATCH p = (m:Group)-[:" + HAS_CHILD + "*]->(r:Group) WHERE id(m) = $ownerId " +
        "RETURN [rel IN relationships(p) | [id(startNode(rel)), id(endNode(rel))]] " +
        "UNION MATCH p = (m)-[role:" + MANAGER_ROLE + "*]->(r:Group) WHERE id(m) = $ownerId AND (m:Group OR m:User) " +
            DATE_VALID_FILTER_ON_DEV_OR_MGR_ROLE +
            "RETURN [rel IN relationships(p) | [id(startNode(rel)), id(endNode(rel))]]"
    )
    Flux<ListValue> ownedGroupWithPaths(@Param("ownerId") long owningUserOrGroupId);

    /**
     * Derived and related to {@link ProjectsRepository#timeLoggableProjects(long)}
     */
    // Excludes child groups. Owns children of group user/group belongs to AND direct manager resources
    @Query(
        "MATCH (r:Group)-[:" + OWNS + "|" + IN_GROUP + "]-(p) WHERE id(r) IN $ofGroupIds RETURN id(p)"
    )
    Flux<Long> ownedResources(@Param("ofGroupIds") Collection<Long> ofGroupIds);

    @Query("MATCH (c:Group),(p:Group) WHERE id(c) = $childId AND id(p) = $parentId MERGE (p)-[:" + HAS_CHILD + "]->(c) RETURN c")
    Mono<Group> mergeToParent(@Param("childId") long childId, @Param("parentId") long parentId);

    @Query("MATCH (u),(g:Group) WHERE id(u) IN $resources AND id(g) IN $groups AND (u:User OR u:Group) CREATE (u)-[:" + IN_GROUP +"]->(g) RETURN COUNT(g)")
    Mono<Long> addUserOrGroupToGroup(@Param("resources") Collection<Long> groupOrUserIds, @Param("groups") Collection<Long> groupIds);

    @Query("MATCH (u)-[role:" + IN_GROUP + "]->(g:Group) WHERE id(u) IN $resources AND id(g) IN $groups AND (u:User OR u:Group) DELETE role RETURN COUNT(role)")
    Mono<Long> removeUserOrGroupFromGroup(@Param("resources") Collection<Long> groupOrUserIds, @Param("groups") Collection<Long> groupIds);

    @Query("MATCH (g:Group),(p:Project) WHERE id(p) IN $projects AND id(g) IN $groups CREATE (g)-[:" + OWNS +"]->(p) RETURN COUNT(g)")
    Mono<Long> addProjectsToGroup(@Param("projects") Collection<Long> projectIds, @Param("groups") Collection<Long> groupIds);

    @Query("MATCH (g:Group)-[role:" + OWNS + "]->(p:Project) WHERE id(p) IN $projects AND id(g) IN $groups DELETE role RETURN COUNT(role)")
    Mono<Long> removeProjectsFromGroup(@Param("projects") Collection<Long> projectIds, @Param("groups") Collection<Long> groupIds);

    @Query("MATCH (go:Group)<-[:IN_GROUP]-(excluded:User) WHERE id(go) <> $groupId " +
            "WITH collect(excluded) as excluded " +
            "MATCH (g:Group) WHERE id(g) = $groupId " +
            "OPTIONAL MATCH (g)<-[:IN_GROUP]-(u:User) WHERE NOT u IN excluded " +
            "DETACH DELETE g,u")
    Mono<Long> removeGroupAndUsersThatAreOnlyInIt(@Param("groupId") long groupId);
}

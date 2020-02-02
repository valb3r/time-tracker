package com.github.valb3r.springbatch.adapters.neo4j.ogm.repository;

import com.github.valb3r.springbatch.adapters.neo4j.ogm.entity.Neo4jJobInstance;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.valb3r.springbatch.adapters.neo4j.ogm.BatchRelationshipConst.PARENT;

@Repository
public interface Neo4jJobInstanceRepository extends CrudRepository<Neo4jJobInstance, Long> {

    @Query("MATCH (i:Neo4jJobInstance) WHERE i.jobName = $jobName AND i.jobKey = $jobKey RETURN i")
    Optional<Neo4jJobInstance> findBy(@Param("jobName") String jobName, @Param("jobKey") String jobKey);

    @Query("MATCH (e:Neo4jJobExecution)-[:" + PARENT + "]->(i:Neo4jJobInstance) WHERE e.id = $jobExecutionId RETURN i")
    Optional<Neo4jJobInstance> findForExecution(@Param("jobExecutionId") long jobExecutionId);

    @Query("MATCH (i:Neo4jJobInstance) WHERE i.jobName = $jobName RETURN i " +
        "ORDER BY i.createdAt DESC " +
        "SKIP $start LIMIT $count")
    List<Neo4jJobInstance> findByJobName(
        @Param("jobName") String jobName,
        @Param("start") int start,
        @Param("count") int count
    );

    @Query("MATCH (i:Neo4jJobInstance) WHERE i.jobName CONTAINS $jobName RETURN i " +
        "ORDER BY i.createdAt DESC " +
        "SKIP $start LIMIT $count")
    List<Neo4jJobInstance> findByLikeJobName(
        @Param("jobName") String jobName,
        @Param("start") int start,
        @Param("count") int count
    );

    @Query("MATCH (i:Neo4jJobInstance) WHERE i.jobName = $jobName RETURN i " +
        "ORDER BY i.jobName DESC")
    List<String> allNames();

    int countAllByJobName();
}

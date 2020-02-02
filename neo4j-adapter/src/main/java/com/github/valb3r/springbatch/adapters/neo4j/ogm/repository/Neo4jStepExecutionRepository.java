package com.github.valb3r.springbatch.adapters.neo4j.ogm.repository;

import com.github.valb3r.springbatch.adapters.neo4j.ogm.entity.Neo4jStepExecution;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Neo4jStepExecutionRepository extends CrudRepository<Neo4jStepExecution, Long> {

    @Query("MATCH (s:Neo4jStepExecution)-[:PARENT]->(e:Neo4jJobExecution) " +
        "WHERE e.id = $jobExecId AND s.id = $stepExecId " +
        "RETURN s")
    Optional<Neo4jStepExecution> findBy(
        @Param("jobExecId") long jobExecId,
        @Param("stepExecId") long stepExecId
    );

    @Query("MATCH (s:Neo4jStepExecution)-[:PARENT]->(e:Neo4jJobExecution)-[:PARENT]->(j:Neo4jJobInstance) " +
        "WHERE j.id = $jobExecInstanceId AND s.stepName = $stepName " +
        "RETURN s ORDER BY s.startTime DESC, s.id DESC")
    List<Neo4jStepExecution> findLastStepExecution(
        @Param("jobExecInstanceId") long jobExecInstanceId,
        @Param("stepName") String stepName
    );
}

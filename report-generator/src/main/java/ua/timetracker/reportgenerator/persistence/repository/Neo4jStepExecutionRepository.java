package ua.timetracker.reportgenerator.persistence.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.timetracker.reportgenerator.persistence.entity.Neo4jStepExecution;

import java.util.Optional;

@Repository
public interface Neo4jStepExecutionRepository extends CrudRepository<Neo4jStepExecution, Long> {

    @Query("MATCH (s:Neo4jStepExecution)-[:PARENT]->(e:Neo4jJobExecution) " +
        "WHERE e.id = {jobExecId} AND s.id = {stepExecId} " +
        "RETURN s")
    Optional<Neo4jStepExecution> findBy(
        @Param("jobExecId") long jobExecId,
        @Param("stepExecId") long stepExecId
    );

}

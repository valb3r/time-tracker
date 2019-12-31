package ua.timetracker.reportgenerator.persistence.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.timetracker.reportgenerator.persistence.entity.Neo4jJobExecution;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface Neo4jJobExecutionRepository extends CrudRepository<Neo4jJobExecution, Long> {

    List<Neo4jJobExecution> findAllByJobInstanceId(long instanceId);

    @Query("MATCH (e:Neo4jJobExecution)-[:PARENT]->(i:Neo4jJobInstance) WHERE i.id = $instanceId RETURN e " +
        "ORDER BY e.createTime LIMIT 1")
    Optional<Neo4jJobExecution> findLatestExecution(@Param("instanceId") long instanceId);

    @Query("MATCH (e:Neo4jJobExecution)-[:PARENT]->(i:Neo4jJobInstance) " +
        "WHERE i.jobName = $name AND e.startTime IS NOT NULL AND e.endTime IS NULL RETURN e " +
        "ORDER BY e.id DESC")
    Set<Neo4jJobExecution> findRunningJobExecutions(@Param("name") String jobName);
}

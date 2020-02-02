package com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j;

import com.github.valb3r.springbatch.adapters.neo4j.ogm.entity.Neo4jJobExecution;
import com.github.valb3r.springbatch.adapters.neo4j.ogm.repository.Neo4jJobExecutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Neo4jJobExecutionDao implements JobExecutionDao {

    private final Neo4jJobExecutionRepository jobExecs;

    @Override
    @Transactional
    public void saveJobExecution(JobExecution jobExecution) {
        val result = jobExecs.save(Neo4jJobExecution.MAP.map(jobExecution, new CycleAvoidingMappingContext()));
        jobExecution.setId(result.getId());
    }

    @Override
    @Transactional
    public void updateJobExecution(JobExecution jobExecution) {
        jobExecs.save(Neo4jJobExecution.MAP.map(jobExecution, new CycleAvoidingMappingContext()));
    }

    @Override
    @Transactional
    public List<JobExecution> findJobExecutions(JobInstance jobInstance) {
        return jobExecs.findAllByJobInstanceId(jobInstance.getId())
            .stream()
            .map(it -> Neo4jJobExecution.MAP.map(it, new CycleAvoidingMappingContext()))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JobExecution getLastJobExecution(JobInstance jobInstance) {
        return jobExecs.findLatestExecution(jobInstance.getId())
            .map(it -> Neo4jJobExecution.MAP.map(it, new CycleAvoidingMappingContext()))
            .orElse(null);
    }

    @Override
    @Transactional
    public Set<JobExecution> findRunningJobExecutions(String jobName) {
        return jobExecs.findRunningJobExecutions(jobName)
            .stream()
            .map(it -> Neo4jJobExecution.MAP.map(it, new CycleAvoidingMappingContext()))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    @Transactional
    public JobExecution getJobExecution(Long executionId) {
        return jobExecs.findById(executionId)
            .map(it -> Neo4jJobExecution.MAP.map(it, new CycleAvoidingMappingContext()))
            .orElse(null);
    }

    @Override
    @Transactional
    public void synchronizeStatus(JobExecution jobExecution) {
        saveJobExecution(jobExecution);
    }
}

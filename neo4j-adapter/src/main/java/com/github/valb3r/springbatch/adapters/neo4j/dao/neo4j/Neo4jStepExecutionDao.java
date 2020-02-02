package com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j;

import com.github.valb3r.springbatch.adapters.neo4j.ogm.repository.Neo4jStepExecutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import com.github.valb3r.springbatch.adapters.neo4j.ogm.entity.Neo4jStepExecution;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.dao.StepExecutionDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Neo4jStepExecutionDao implements StepExecutionDao {

    private final Neo4jStepExecutionRepository stepExecs;

    @Override
    @Transactional
    public void saveStepExecution(StepExecution stepExecution) {
        val exec = stepExecs.save(Neo4jStepExecution.MAP.map(stepExecution));
        stepExecution.setId(exec.getId());
    }

    @Override
    @Transactional
    public void saveStepExecutions(Collection<StepExecution> stepExecutions) {
        stepExecs.saveAll(
            stepExecutions.stream().map(Neo4jStepExecution.MAP::map).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public void updateStepExecution(StepExecution stepExecution) {
        saveStepExecution(stepExecution);
    }

    @Override
    @Transactional
    public StepExecution getStepExecution(JobExecution jobExecution, Long stepExecutionId) {
        return stepExecs.findBy(jobExecution.getId(), stepExecutionId)
            .map(Neo4jStepExecution.MAP::map)
            .orElse(null);
    }

    @Override
    public StepExecution getLastStepExecution(JobInstance jobInstance, String stepName) {
        List<Neo4jStepExecution> executions = stepExecs.findLastStepExecution(jobInstance.getInstanceId(), stepName);

        if (executions.isEmpty()) {
            return null;
        } else {
            return Neo4jStepExecution.MAP.map(executions.get(0));
        }
    }

    @Override
    @Transactional
    public void addStepExecutions(JobExecution jobExecution) {
        // NOP
    }
}

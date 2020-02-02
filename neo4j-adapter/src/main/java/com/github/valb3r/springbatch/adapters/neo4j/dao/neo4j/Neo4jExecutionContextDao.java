package com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j;

import com.github.valb3r.springbatch.adapters.neo4j.ogm.repository.Neo4jJobExecutionRepository;
import com.github.valb3r.springbatch.adapters.neo4j.ogm.repository.Neo4jStepExecutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Neo4jExecutionContextDao implements ExecutionContextDao {

    private final Neo4jJobExecutionRepository jobExecs;
    private final Neo4jStepExecutionRepository stepExecs;

    @Override
    @Transactional
    public ExecutionContext getExecutionContext(JobExecution jobExecution) {
        val execution = jobExecs.findById(jobExecution.getId());
        return execution
            .map(it -> new ExecutionContext(it.getExecutionContext()))
            .orElseGet(ExecutionContext::new);
    }

    @Override
    @Transactional
    public ExecutionContext getExecutionContext(StepExecution stepExecution) {
        val execution = stepExecs.findById(stepExecution.getId());
        return execution
            .map(it -> new ExecutionContext(it.getExecutionContext()))
            .orElseGet(ExecutionContext::new);
    }

    @Override
    @Transactional
    public void saveExecutionContext(JobExecution jobExecution) {
        val execution = jobExecs.findById(jobExecution.getId()).get();
        execution.setExecutionContext(
            serializeContext(jobExecution.getExecutionContext())
        );

        jobExecs.save(execution);
    }

    @Override
    @Transactional
    public void saveExecutionContext(StepExecution stepExecution) {
        val execution = stepExecs.findById(stepExecution.getId()).get();
        execution.setExecutionContext(
            serializeContext(stepExecution.getExecutionContext())
        );

        stepExecs.save(execution);
    }

    @Override
    @Transactional
    public void saveExecutionContexts(Collection<StepExecution> stepExecutions) {
        stepExecutions.forEach(this::saveExecutionContext);

    }

    @Override
    @Transactional
    public void updateExecutionContext(JobExecution jobExecution) {
        saveExecutionContext(jobExecution);
    }

    @Override
    @Transactional
    public void updateExecutionContext(StepExecution stepExecution) {
        saveExecutionContext(stepExecution);
    }

    private Map<String, Object> serializeContext(ExecutionContext ctx) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> me : ctx.entrySet()) {
            result.put(me.getKey(), me.getValue());
        }

        return result;
    }
}

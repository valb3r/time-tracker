package ua.timetracker.reportgenerator.config.neo4jbatch.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.dao.StepExecutionDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.timetracker.reportgenerator.persistence.entity.Neo4jStepExecution;
import ua.timetracker.reportgenerator.persistence.repository.Neo4jStepExecutionRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Neo4jStepExecutionDao implements StepExecutionDao {

    private final Neo4jStepExecutionRepository stepExecs;

    @Override
    @Transactional
    public void saveStepExecution(StepExecution stepExecution) {
        stepExecs.save(Neo4jStepExecution.MAP.map(stepExecution));
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
        return Neo4jStepExecution.MAP.map(
            stepExecs.findBy(jobExecution.getId(), stepExecutionId).get()
        );
    }

    @Override
    @Transactional
    public void addStepExecutions(JobExecution jobExecution) {
        // NOP
    }
}

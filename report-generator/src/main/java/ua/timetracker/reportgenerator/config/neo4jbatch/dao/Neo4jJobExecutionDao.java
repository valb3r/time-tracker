package ua.timetracker.reportgenerator.config.neo4jbatch.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.timetracker.reportgenerator.persistence.entity.Neo4jJobExecution;
import ua.timetracker.reportgenerator.persistence.repository.Neo4jJobExecutionRepository;

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
        jobExecs.save(Neo4jJobExecution.MAP.map(jobExecution));
    }

    @Override
    @Transactional
    public void updateJobExecution(JobExecution jobExecution) {
        jobExecs.save(Neo4jJobExecution.MAP.map(jobExecution));
    }

    @Override
    @Transactional
    public List<JobExecution> findJobExecutions(JobInstance jobInstance) {
        return jobExecs.findAllByJobInstanceId(jobInstance.getId())
            .stream()
            .map(Neo4jJobExecution.MAP::map)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JobExecution getLastJobExecution(JobInstance jobInstance) {
        return Neo4jJobExecution.MAP.map(
            jobExecs.findLatestExecution(jobInstance.getId()).get()
        );
    }

    @Override
    @Transactional
    public Set<JobExecution> findRunningJobExecutions(String jobName) {
        return jobExecs.findRunningJobExecutions(jobName)
            .stream()
            .map(Neo4jJobExecution.MAP::map)
            .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public JobExecution getJobExecution(Long executionId) {
        return Neo4jJobExecution.MAP.map(
            jobExecs.findById(executionId).get()
        );
    }

    @Override
    @Transactional
    public void synchronizeStatus(JobExecution jobExecution) {
        saveJobExecution(jobExecution);
    }
}

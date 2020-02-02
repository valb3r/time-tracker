package com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j;

import com.github.valb3r.springbatch.adapters.neo4j.ogm.entity.Neo4jJobInstance;
import com.github.valb3r.springbatch.adapters.neo4j.ogm.repository.Neo4jJobInstanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.core.DefaultJobKeyGenerator;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobKeyGenerator;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.dao.JobInstanceDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Neo4jJobInstanceDao implements JobInstanceDao {

    private final Neo4jJobInstanceRepository jobInstances;

    private final JobKeyGenerator<JobParameters> keyGenerator = new DefaultJobKeyGenerator();

    @Override
    @Transactional
    public JobInstance createJobInstance(String jobName, JobParameters jobParameters) {
        val saved = jobInstances.save(
            Neo4jJobInstance.builder()
                .jobName(jobName)
                .jobKey(keyGenerator.generateKey(jobParameters))
                .parameters(jobParameters.getParameters())
                .build()
        );

        return Neo4jJobInstance.MAP.map(saved);
    }

    @Override
    @Transactional
    public JobInstance getJobInstance(String jobName, JobParameters jobParameters) {
        return jobInstances.findBy(jobName, keyGenerator.generateKey(jobParameters))
            .map(Neo4jJobInstance.MAP::map)
            .orElse(null);
    }

    @Override
    @Transactional
    public JobInstance getJobInstance(Long instanceId) {
        return jobInstances.findById(instanceId)
            .map(Neo4jJobInstance.MAP::map)
            .orElse(null);
    }

    @Override
    @Transactional
    public JobInstance getJobInstance(JobExecution jobExecution) {
        return jobInstances.findForExecution(jobExecution.getJobId())
            .map(Neo4jJobInstance.MAP::map)
            .orElse(null);
    }

    @Override
    @Transactional
    public List<JobInstance> getJobInstances(String jobName, int start, int count) {
        return jobInstances.findByJobName(jobName, start, count).stream()
            .map(Neo4jJobInstance.MAP::map)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<String> getJobNames() {
        return jobInstances.allNames();
    }

    @Override
    @Transactional
    public List<JobInstance> findJobInstancesByName(String jobName, int start, int count) {
        return jobInstances.findByLikeJobName(jobName, start, count).stream()
            .map(Neo4jJobInstance.MAP::map)
            .collect(Collectors.toList());
    }

    @Override
    public int getJobInstanceCount(String jobName) throws NoSuchJobException {
        return jobInstances.countAllByJobName();
    }
}

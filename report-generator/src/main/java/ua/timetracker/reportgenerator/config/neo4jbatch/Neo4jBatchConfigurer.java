package ua.timetracker.reportgenerator.config.neo4jbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import ua.timetracker.reportgenerator.config.neo4jbatch.dao.Neo4jExecutionContextDao;
import ua.timetracker.reportgenerator.config.neo4jbatch.dao.Neo4jJobExecutionDao;
import ua.timetracker.reportgenerator.config.neo4jbatch.dao.Neo4jJobInstanceDao;
import ua.timetracker.reportgenerator.config.neo4jbatch.dao.Neo4jStepExecutionDao;

@Component
@RequiredArgsConstructor
public class Neo4jBatchConfigurer implements BatchConfigurer {

    private final Neo4jExecutionContextDao executionContextDao;
    private final Neo4jJobExecutionDao executionDao;
    private final Neo4jJobInstanceDao instanceDao;
    private final Neo4jStepExecutionDao stepExecutionDao;
    private final Neo4jTransactionManager txManager;

    @Override
    public JobRepository getJobRepository() {
        return new SimpleJobRepository(instanceDao, executionDao, stepExecutionDao, executionContextDao);
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return txManager;
    }

    @Override
    public JobLauncher getJobLauncher() {
        return new SimpleJobLauncher();
    }

    @Override
    public JobExplorer getJobExplorer() {
        return new SimpleJobExplorer(instanceDao, executionDao, stepExecutionDao, executionContextDao);
    }
}

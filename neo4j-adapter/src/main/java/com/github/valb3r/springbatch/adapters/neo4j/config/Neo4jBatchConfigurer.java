package com.github.valb3r.springbatch.adapters.neo4j.config;

import com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j.Neo4jExecutionContextDao;
import com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j.Neo4jJobExecutionDao;
import com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j.Neo4jJobInstanceDao;
import com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j.Neo4jStepExecutionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EntityScan(basePackages = {
    "com.github.valb3r.springbatch.adapters.neo4j.ogm.entity"
})
@ComponentScan(
    basePackages = {
        "com.github.valb3r.springbatch.adapters.neo4j.dao.neo4j",
        "com.github.valb3r.springbatch.adapters.neo4j.ogm"
    }
)
@EnableNeo4jRepositories(
    basePackages = {
        "com.github.valb3r.springbatch.adapters.neo4j.ogm.repository"
    }
)
@RequiredArgsConstructor
public class Neo4jBatchConfigurer implements BatchConfigurer {

    private final Neo4jExecutionContextDao executionContextDao;
    private final Neo4jJobExecutionDao executionDao;
    private final Neo4jJobInstanceDao instanceDao;
    private final Neo4jStepExecutionDao stepExecutionDao;

    @Override
    public JobRepository getJobRepository() {
        return new SimpleJobRepository(instanceDao, executionDao, stepExecutionDao, executionContextDao);
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        // TODO Check transaction management
        return new ResourcelessTransactionManager();
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

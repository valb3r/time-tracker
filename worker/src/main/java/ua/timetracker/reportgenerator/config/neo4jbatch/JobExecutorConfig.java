package ua.timetracker.reportgenerator.config.neo4jbatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class JobExecutorConfig {

    public static final String JOB_EXEC_POOL = "JOB_EXEC_POOL";

    @Bean
    public ThreadPoolTaskExecutor taskExecutor(@Value("${jobs.pool}") int poolSize) {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadGroupName("worker");
        pool.setThreadNamePrefix("task-");
        pool.setCorePoolSize(poolSize);
        pool.setMaxPoolSize(poolSize);
        return pool;
    }
}

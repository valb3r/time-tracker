package ua.timetracker.reportgenerator;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.neo4j.annotation.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = {
        "ua.timetracker.shared.persistence.entity",
        "ua.timetracker.reportgenerator.persistence.repository.imperative.dto"
})
@EnableScheduling
@EnableBatchProcessing
@EnableNeo4jAuditing
@EnableNeo4jRepositories
@EnableConfigurationProperties
@SpringBootApplication(
    scanBasePackages = {
        "ua.timetracker.shared.config",
        "ua.timetracker.shared.persistence",
        "ua.timetracker.reportgenerator.config",
        "ua.timetracker.reportgenerator.persistence",
        "ua.timetracker.reportgenerator.service"
    },
    exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
    }
)
public class ReportGenerationServer {

    public static void main(String[] args) {
        SpringApplication.run(ReportGenerationServer.class, args);
    }
}

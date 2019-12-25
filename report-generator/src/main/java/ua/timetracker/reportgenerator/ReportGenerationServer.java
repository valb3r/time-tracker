package ua.timetracker.reportgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.neo4j.annotation.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@EnableNeo4jAuditing
@EnableNeo4jRepositories
@EnableConfigurationProperties
@SpringBootApplication(
    scanBasePackages = {
        "ua.timetracker.reportgenerator.config",
        "ua.timetracker.shared.config"
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

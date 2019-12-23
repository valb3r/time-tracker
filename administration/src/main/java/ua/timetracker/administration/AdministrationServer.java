package ua.timetracker.administration;

import org.neo4j.springframework.data.config.EnableNeo4jAuditing;
import org.neo4j.springframework.data.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableNeo4jAuditing
@EnableReactiveNeo4jRepositories("ua.timetracker.shared.persistence.repository.reactive")
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication(
        scanBasePackages = {
                "ua.timetracker.shared.config",
                "ua.timetracker.administration.config",
                "ua.timetracker.administration.controller",
                "ua.timetracker.administration.service"
        }
)
public class AdministrationServer {

    public static void main(String[] args) {
        SpringApplication.run(AdministrationServer.class, args);
    }
}

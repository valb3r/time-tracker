package ua.timetracker.timetrackingserver;

import org.neo4j.springframework.data.config.EnableNeo4jAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableNeo4jAuditing
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication(
        scanBasePackages = {
                "ua.timetracker.timetrackingserver.config",
                "ua.timetracker.timetrackingserver.controller",
                "ua.timetracker.timetrackingserver.repository",
                "ua.timetracker.timetrackingserver.service"
        }
)
public class TimeTrackingServer {

    public static void main(String[] args) {
        SpringApplication.run(TimeTrackingServer.class, args);
    }
}

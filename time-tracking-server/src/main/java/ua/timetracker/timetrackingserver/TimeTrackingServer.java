package ua.timetracker.timetrackingserver;

import org.neo4j.springframework.data.config.EnableNeo4jAuditing;
import org.neo4j.springframework.data.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.tools.agent.ReactorDebugAgent;

@EnableNeo4jAuditing
@EnableReactiveNeo4jRepositories("ua.timetracker.shared.persistence.repository.reactive")
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication(
        scanBasePackages = {
                "ua.timetracker.shared.config",
                "ua.timetracker.timetrackingserver.config",
                "ua.timetracker.timetrackingserver.controller",
                "ua.timetracker.timetrackingserver.service"
        }
)
public class TimeTrackingServer {

    public static void main(String[] args) {
        ReactorDebugAgent.init(); //  Requires JDK, Should be not costly as is bytecode generated, otherwise no stacktraces in logs
        SpringApplication.run(TimeTrackingServer.class, args);
    }
}

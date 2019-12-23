package ua.timetracker.administration.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.springframework.data.core.ReactiveNeo4jClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import java.util.List;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Neo4jSchemaManagement {

    private final ReactiveNeo4jClient client;
    private final Schema neo4jSchema;

    @PostConstruct
    @Transactional(REACTIVE_TX_MANAGER)
    public void createIndexesAndConstraints() {
        // TODO: Schema management solution
        neo4jSchema.getSchema().forEach(statement -> client.query(statement).run()
                .onErrorResume(ex -> {
                    if (ex.getCause().getMessage().contains("An equivalent constraint already exists")) {
                        log.info("{} seem to be already executed", statement);
                        return Mono.empty();
                    }

                    throw new RuntimeException(ex);
                }).block());
    }

    @Data
    @Configuration
    @ConfigurationProperties("time-tracker.neo4j")
    static class Schema {

        private List<String> schema;
    }
}

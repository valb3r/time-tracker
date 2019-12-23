package ua.timetracker.shared.config;

import lombok.RequiredArgsConstructor;
import org.neo4j.springframework.data.core.ReactiveNeo4jClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Configuration
@RequiredArgsConstructor
public class Neo4jIndexes {

    private final ReactiveNeo4jClient client;

    @PostConstruct
    @Transactional(REACTIVE_TX_MANAGER)
    public void createIndexesAndConstraints() {
        // TODO: Schema management solution
        client.query("CREATE CONSTRAINT ON (user:User) ASSERT user.name IS UNIQUE").run()
                .onErrorResume(ex -> {
                    if (ex.getCause().getMessage().contains("An equivalent constraint already exists")) {
                        return Mono.empty();
                    }

                    throw new RuntimeException(ex);
                }).block();
    }
}

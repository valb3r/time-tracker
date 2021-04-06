package ua.timetracker.reportgenerator.config.neo4jbatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}

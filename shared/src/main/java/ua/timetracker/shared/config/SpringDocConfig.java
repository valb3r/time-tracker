package ua.timetracker.shared.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}

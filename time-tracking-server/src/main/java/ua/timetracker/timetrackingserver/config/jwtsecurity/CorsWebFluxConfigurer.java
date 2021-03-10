package ua.timetracker.timetrackingserver.config.jwtsecurity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CorsWebFluxConfigurer implements WebFluxConfigurer {

    private final CorsConfig corsConfig;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(corsConfig.getOrigins())
                .allowCredentials(true)
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    @Valid
    @Data
    @Configuration
    @ConfigurationProperties(prefix = "cors")
    public static class CorsConfig {

        @NotEmpty
        private String[] origins;
    }
}

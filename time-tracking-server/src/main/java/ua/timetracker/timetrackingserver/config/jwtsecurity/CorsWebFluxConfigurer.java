package ua.timetracker.timetrackingserver.config.jwtsecurity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Slf4j
@Configuration
public class CorsWebFluxConfigurer implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("http://localhost:28001", "http://localhost:6500")
            .exposedHeaders("Access-Control-Allow-Headers: Content-Type, *")
            .allowCredentials(true)
            .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }
}

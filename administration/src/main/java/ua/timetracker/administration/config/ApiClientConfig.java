package ua.timetracker.administration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("api.client")
public class ApiClientConfig {

    private Long minClientVersion;
}

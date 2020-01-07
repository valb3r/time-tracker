package ua.timetracker.timetrackingserver.config.jwtsecurity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Data
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Config {

    @NotNull
    private Key keys;

    @Data
    @Validated
    public static class Key {

        @NotNull
        private String pub;
    }
}

package ua.timetracker.administration.config.jwtsecurity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Validated
@Data
@Configuration
@ConfigurationProperties("ouath2")
public class Oauth2Config {

    @NotNull
    private Key keys;

    @Data
    public static class Key {

        @NotNull
        private RSAPublicKey pub;

        @NotBlank
        private RSAPrivateKey priv;
    }
}

package ua.timetracker.administration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncryptionConfig {

    @Bean
    PasswordEncoder encoder(@Value("${bcrypt.strength}") int strength) {
        return new BCryptPasswordEncoder(strength);
    }
}

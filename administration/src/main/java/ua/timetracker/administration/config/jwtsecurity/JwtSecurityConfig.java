package ua.timetracker.administration.config.jwtsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class JwtSecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveJwtDecoder decoder) {
        http
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
          //  .bearerTokenConverter(new CookieBasedJwt())
            .jwt()
            .jwtDecoder(decoder);
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder(Oauth2Config oauth2) {
        return NimbusReactiveJwtDecoder.withPublicKey(oauth2.getKeys().getPub()).build();
    }

    /**
    static class CookieBasedJwt implements ServerAuthenticationConverter {

        @Override
        public Mono<Authentication> convert(ServerWebExchange exchange) {
            return exchange.getRequest().getCookies().getFirst();
        }
    }*/
}

package ua.timetracker.administration.config.jwtsecurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.interfaces.RSAPublicKey;

import static ua.timetracker.shared.restapi.Paths.V1_RESOURCES;


@Configuration
@EnableWebFluxSecurity
public class ResourceServerJwtSecurityConfig {

    public static final String AUTHORIZATION = "X-Authorization";

    @Value("${oauth2.keys.pub}")
    private RSAPublicKey publicKey;

    /**
     * Protects /v1/resources with bearer-alike cookie 'X-Authorization'
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf().disable()
            .securityMatcher(new PathPatternParserServerWebExchangeMatcher(V1_RESOURCES + "/**"))
            .oauth2ResourceServer()
                .bearerTokenConverter(new CookieBasedJwt())
                .jwt()
                .jwtDecoder(jwtDecoder());
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withPublicKey(publicKey).build();
    }

    static class CookieBasedJwt implements ServerAuthenticationConverter {

        @Override
        public Mono<Authentication> convert(ServerWebExchange exchange) {
            return Mono.justOrEmpty(readToken(exchange));
        }

        private BearerTokenAuthenticationToken readToken(ServerWebExchange exchange) {
            HttpCookie cookie = exchange.getRequest().getCookies().getFirst(AUTHORIZATION);
            if (null == cookie) {
                return null;
            }

            return new BearerTokenAuthenticationToken(cookie.getValue());
        }
    }
}

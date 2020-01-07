package ua.timetracker.administration.config.jwtsecurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.interfaces.RSAPublicKey;

import static ua.timetracker.shared.config.Const.AUTHORIZATION_COOKIE;
import static ua.timetracker.shared.restapi.Paths.V1_RESOURCES;


@Configuration
@EnableWebFluxSecurity
// @EnableReactiveMethodSecurity - useless, see: https://github.com/spring-projects/spring-security/issues/4841
public class ResourceServerJwtSecurityConfig {

    @Value("${oauth2.keys.pub}")
    private RSAPublicKey publicKey;

    /**
     * Protects /v1/resources with bearer-alike cookie 'X-Authorization'
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(CorsConfigurationSource corsConfig, ServerHttpSecurity http) {
        http
            .csrf().disable()
            .cors(corsSpec -> corsSpec.configurationSource(corsConfig))
            // OPTIONS preflight is caught by 'oauth2ResourceServer'
            .securityMatcher(
                new OrServerWebExchangeMatcher(
                    new PathPatternParserServerWebExchangeMatcher(V1_RESOURCES + "/**", HttpMethod.GET),
                    new PathPatternParserServerWebExchangeMatcher(V1_RESOURCES + "/**", HttpMethod.POST),
                    new PathPatternParserServerWebExchangeMatcher(V1_RESOURCES + "/**", HttpMethod.PUT),
                    new PathPatternParserServerWebExchangeMatcher(V1_RESOURCES + "/**", HttpMethod.DELETE)
                )
            )
            .authorizeExchange().anyExchange().authenticated().and()
            .oauth2ResourceServer()
                .bearerTokenConverter(new CookieBasedJwt())
                .jwt()
                .jwtDecoder(jwtDecoder());
        return http.build();
    }

    @Bean
    CorsConfigurationSource cors() {
        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedOrigin("localhost:6500");
        cors.registerCorsConfiguration(V1_RESOURCES + "/**", configuration);
        return cors;
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
            HttpCookie cookie = exchange.getRequest().getCookies().getFirst(AUTHORIZATION_COOKIE);
            if (null == cookie) {
                return null;
            }

            return new BearerTokenAuthenticationToken(cookie.getValue());
        }
    }
}

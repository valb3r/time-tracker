package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.config.ApiClientConfig;
import ua.timetracker.administration.domain.TokenDto;
import ua.timetracker.administration.service.LoginAuthorization;
import ua.timetracker.shared.restapi.dto.user.LoginDto;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ua.timetracker.shared.config.Const.AUTHORIZATION_COOKIE;
import static ua.timetracker.shared.restapi.Paths.V1_LOGIN;

@RestController
@RequestMapping(value = V1_LOGIN)
@RequiredArgsConstructor
public class LoginController {

    private final ApiClientConfig config;
    private final LoginAuthorization authorization;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Mono<? extends ResponseEntity> login(
        @RequestBody @Valid LoginDto loginDto, @Parameter(hidden = true) ServerHttpResponse response
    ) {
        if (null != config.getMinClientVersion()
                && (null == loginDto.getClientversion() || (null != loginDto.getClientversion() && config.getMinClientVersion() > loginDto.getClientversion()))
        ) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please use newer tracker client"));
        }
        return authorization.issueTokenIfAuthorized(loginDto.getUsername(), loginDto.getPassword())
            .map(user -> buildResponse(user, response))
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @DeleteMapping
    public Mono<? extends ResponseEntity> logout(@Parameter(hidden = true) ServerHttpResponse response) {
        return Mono.just(buildResponse(new TokenDto(), response));
    }

    private ResponseEntity buildResponse(TokenDto user, ServerHttpResponse response) {
        response.addCookie(
            ResponseCookie.from(AUTHORIZATION_COOKIE, user.getIssuedToken())
                .path("/")
                .httpOnly(true)
                .build()
        );
        return ResponseEntity.ok().body(user.getUser());
    }
}

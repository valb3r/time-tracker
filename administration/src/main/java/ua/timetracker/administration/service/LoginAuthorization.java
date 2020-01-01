package ua.timetracker.administration.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.domain.TokenDto;
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import java.security.interfaces.RSAPrivateKey;
import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class LoginAuthorization {

    @Value("${oauth2.keys.priv}")
    private RSAPrivateKey privateKey;

    @Value("${oauth2.token.validity}")
    private Duration validity;

    private final UsersRepository users;
    private final PasswordEncoder encoder;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<TokenDto> issueTokenIfAuthorized(String username, String password) {
        return users.findByName(username)
            .filter(it -> encoder.matches(password, it.getEncodedPassword()))
            .map(this::buildToken);
    }

    private TokenDto buildToken(User forUser) {
        String token = doGenerateToken(forUser);
        return new TokenDto(UserDto.MAP.map(forUser), token);
    }

    @SneakyThrows
    private String doGenerateToken(User forUser) {
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
            .expirationTime(Date.from(currentTime.plus(validity).toInstant()))
            .issueTime(Date.from(currentTime.toInstant()))
            .subject(String.valueOf(forUser.getId()))
            .build();

        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, claims);
        signedJWT.sign(new RSASSASigner(privateKey));
        return signedJWT.serialize();
    }
}

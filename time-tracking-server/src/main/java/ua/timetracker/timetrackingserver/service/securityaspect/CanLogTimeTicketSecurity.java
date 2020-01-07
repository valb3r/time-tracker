package ua.timetracker.timetrackingserver.service.securityaspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.restapi.ForbiddenException;
import ua.timetracker.shared.restapi.dto.WithProjectId;

import java.lang.reflect.Parameter;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.util.UserIdUtil.id;

/**
 * Can't use @PreAuthorize as it requires boolean/Boolean return values in evaluator.
 * see: https://github.com/spring-projects/spring-security/issues/4841
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CanLogTimeTicketSecurity {

    private final ProjectsRepository projects;

    @Transactional(REACTIVE_TX_MANAGER)
    @Around(value = "execution(" +
        "public reactor.core.publisher.Mono+ " +
        "*(.., (org.springframework.security.core.Authentication+), .., (ua.timetracker.shared.restapi.dto.WithProjectId+), ..)" +
        ") " +
        "&& (@annotation(ann) || @within(ann))")
    public Mono<?> protectMono(ProceedingJoinPoint joinPoint, CanLogTimeTicket ann) {
        return canLogTicket(joinPoint).filter(it -> it)
            .flatMap(it -> {
                try {

                    return ((Mono<?>) joinPoint.proceed());
                } catch (Throwable throwable) {
                    return Mono.error(throwable);
                }
            })
            .switchIfEmpty(Mono.error(new ForbiddenException()));
    }

    @Transactional(REACTIVE_TX_MANAGER)
    @Around(value = "execution(" +
        "public reactor.core.publisher.Flux+ " +
        "*(.., (org.springframework.security.core.Authentication+), .., (ua.timetracker.shared.restapi.dto.WithProjectId+), ..)" +
        ") " +
        "&& (@annotation(ann) || @within(ann))")
    public Flux<?> protectFlux(ProceedingJoinPoint joinPoint, CanLogTimeTicket ann) {
        return canLogTicket(joinPoint).filter(it -> it)
            .flatMapMany(it -> {
                try {

                    return ((Flux<?>) joinPoint.proceed());
                } catch (Throwable throwable) {
                    return Flux.error(throwable);
                }
            })
            .switchIfEmpty(Flux.error(new ForbiddenException()));
    }

    private Mono<Boolean> canLogTicket(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] params = signature.getMethod().getParameters();
        Authentication auth = (Authentication) joinPoint.getArgs()[findPosByType(params, Authentication.class)];
        WithProjectId withId = (WithProjectId) joinPoint.getArgs()[findPosByType(params, WithProjectId.class)];

        return projects.timeLoggableProjects(id(auth))
            .any(it -> it.getId().equals(withId.getProjectId()));
    }

    private int findPosByType(Parameter[] params, Class<?> clazz) {
        for (int pos = 0; pos < params.length; ++pos) {
            if (clazz.isAssignableFrom(params[pos].getType())) {
                return pos;
            }
        }

        return -1;
    }
}

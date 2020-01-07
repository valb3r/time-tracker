package ua.timetracker.administration.service.securityaspect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.repository.reactive.GroupsRepository;
import ua.timetracker.shared.restapi.ForbiddenException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private final GroupsRepository groups;

    @Transactional(REACTIVE_TX_MANAGER)
    @Around(value = "execution(" +
        "public reactor.core.publisher.Mono+ " +
        "*(.., (org.springframework.security.core.Authentication+), .., @ManagedResourceId (*), ..)" +
        ") " +
        "&& (@annotation(ann) || @within(ann))")
    public Mono<?> protectMono(ProceedingJoinPoint joinPoint, OnlyResourceManagers ann) {
        return hasAccess(joinPoint).filter(it -> it)
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
        "*(.., (org.springframework.security.core.Authentication+), .., @ManagedResourceId (*), ..)" +
        ") " +
        "&& (@annotation(ann) || @within(ann))")
    public Flux<?> protectFlux(ProceedingJoinPoint joinPoint, OnlyResourceManagers ann) {
        return hasAccess(joinPoint).filter(it -> it)
            .flatMapMany(it -> {
                try {

                    return ((Flux<?>) joinPoint.proceed());
                } catch (Throwable throwable) {
                    return Flux.error(throwable);
                }
            })
            .switchIfEmpty(Flux.error(new ForbiddenException()));
    }

    private Mono<Boolean> hasAccess(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] params = signature.getMethod().getParameters();
        Object[] args = joinPoint.getArgs();

        Authentication auth = (Authentication) joinPoint.getArgs()[findPosByType(params, Authentication.class)];
        Set<Long> ids = extractIds(findAnnotated(params, args, ManagedResourceId.class));

        val ownedGroups = groups.ownedGroupIds(id(auth)).collectList();
        val ownedResources = ownedGroups.flatMapMany(groups::ownedResources).collectList();

        return ownedGroups
            .zipWith(ownedResources)
            .map(owned -> ImmutableList.copyOf(Iterables.concat(owned.getT1(), owned.getT2())).containsAll(ids));
    }

    private int findPosByType(Parameter[] params, Class<?> clazz) {
        for (int pos = 0; pos < params.length; ++pos) {
            if (clazz.isAssignableFrom(params[pos].getType())) {
                return pos;
            }
        }

        return -1;
    }

    private List<Object> findAnnotated(Parameter[] params, Object[] args, Class<? extends Annotation> annotation) {
        List<Object> result = new ArrayList<>();
        for (int pos = 0; pos < params.length; ++pos) {
            if (params[pos].isAnnotationPresent(annotation)) {
                result.add(args[pos]);
            }
        }

        return result;
    }

    private Set<Long> extractIds(List<Object> params) {
        Set<Long> result = new HashSet<>();

        for (Object param : params) {
            if (param instanceof Long) {
                result.add((Long) param);
            }

            if (param instanceof Collection) {
                result.addAll((Collection) param);
            }
        }

        return result;
    }
}

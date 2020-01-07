package ua.timetracker.timetrackingserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogUpload;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.util.UserIdUtil.id;

// Unfortunately Spring security does not support reactive permission evaluation using method level via PreAuthorize.
// See https://github.com/spring-projects/spring-security/issues/4841
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final ProjectsRepository projects;

    /**
     * Returns Mono.true if possible or empty mono if not.
     */
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Boolean> canLogTimeHere(Authentication user, TimeLogUpload log) {
        return projects.timeLoggableProjects(id(user))
            .any(it -> it.getId().equals(log.getProjectId()))
            .flatMap(it -> it ? Mono.just(true) : Mono.empty());
    }
}

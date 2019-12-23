package ua.timetracker.timetrackingserver.service.upload;

import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.TimeLog;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.TimeLogUpload;
import ua.timetracker.shared.restapi.dto.TimeLogUploaded;

import static reactor.core.publisher.Mono.just;
import static ua.timetracker.timetrackingserver.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class TimeLogUploader {

    private final UsersRepository users;
    private final ProjectsRepository projects;
    private final TimeLogsRepository logs;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<TimeLogUploaded> upload(long userId, TimeLogUpload uploaded) {

        return users.findById(just(userId))
                .map(user -> new TimeLog(uploaded).toBuilder().user(user))
                .zipWith(projects.findById(just(uploaded.getProjectId())))
                .map(logAndProject -> logAndProject.getT1().projects(ImmutableSet.of(logAndProject.getT2())).build())
                .flatMap(log -> logs.save(log).map(TimeLogUploaded::new));
    }
}

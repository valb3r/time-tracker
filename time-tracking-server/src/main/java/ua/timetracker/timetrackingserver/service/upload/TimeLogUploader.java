package ua.timetracker.timetrackingserver.service.upload;

import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogUpload;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogDto;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static reactor.core.publisher.Mono.just;


@Service
@RequiredArgsConstructor
public class TimeLogUploader {

    private final UsersRepository users;
    private final ProjectsRepository projects;
    private final TimeLogsRepository timeLogs;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<TimeLogDto> upload(long userId, TimeLogUpload uploaded) {

        val user = users.findById(just(userId));
        val project = projects.findById(just(uploaded.getProjectId()));

        return user.map(usr -> TimeLog.MAP.map(uploaded).toBuilder().user(usr))
                .zipWith(project, (timeLog, proj) -> timeLog.projects(ImmutableSet.of(proj)).build())
                .flatMap(log -> timeLogs.save(log).map(TimeLogDto.MAP::map));
    }
}

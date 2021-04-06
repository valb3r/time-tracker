package ua.timetracker.timetrackingserver.service.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogCreateOrUpdate;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogDto;

import java.time.Duration;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class TimeLogUpdater {

    private final TimeLogsRepository timeLogs;

    @Transactional(value = REACTIVE_TX_MANAGER, isolation = Isolation.SERIALIZABLE)
    public Mono<TimeLogDto> increment(long userId, long id, Duration duration) {
        return timeLogs.findById(id)
                .flatMap(it -> {
                    if (it.getUser().getId() != userId) {
                        return Mono.empty();
                    }

                    it.setDuration(duration.plus(null == it.getDuration() ? Duration.ZERO : it.getDuration()));
                    return timeLogs.save(it);
                }).map(TimeLogDto.MAP::map);
    }

    public Mono<TimeLogDto> update(long userId, long id, TimeLogCreateOrUpdate newLog) {
        return timeLogs.findById(id)
            .flatMap(it -> {
                if (it.getUser().getId() != userId) {
                    return Mono.empty();
                }

                TimeLog.UPDATE.update(newLog, it);
                return timeLogs.save(it);
            }).map(TimeLogDto.MAP::map);
    }

    public Mono<Void> delete(long userId, long id) {
        return timeLogs.findByIdAndUserId(id, userId)
            .flatMap(timeLogs::delete);
    }
}

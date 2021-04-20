package ua.timetracker.timetrackingserver.service.update;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogCreateOrUpdate;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogDto;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class TimeLogUpdater {

    private final TimeLogsRepository timeLogs;

    // TODO - need to obtain locks to increment properly in concurrent setting. Serializable is not supported
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<TimeLogDto> increment(long userId, long id, Duration duration, Set<String> incrementTags) {
        return timeLogs.findById(id)
                .flatMap(it -> {
                    if (it.getUser().getId() != userId) {
                        return Mono.empty();
                    }

                    updateIncrementTags(duration, incrementTags, it);

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

    private void updateIncrementTags(Duration duration, Set<String> incrementTags, TimeLog log) {
        if (null == incrementTags) {
            return;
        }

        val existingTags = log.parseIncrementTags();
        incrementTags.forEach(tag -> existingTags.compute(tag, (tagId, value) -> {
            if (null == tagId || null == value) {
                return duration;
            }
            return value.plus(duration);
        }));

        log.setIncrementTags(existingTags.entrySet().stream().map(it -> it.getKey() + ":" + it.getValue().toString()).collect(Collectors.toSet()));
    }
}

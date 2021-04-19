package ua.timetracker.timetrackingserver.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.projects.TimeLogImage;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogImagesRepository;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.restapi.EntityNotFoundException;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogCreateOrUpdate;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogDto;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogImageDto;
import ua.timetracker.timetrackingserver.config.ImageUploadConfig;
import ua.timetracker.timetrackingserver.service.TimeLogImagesService;
import ua.timetracker.timetrackingserver.service.securityaspect.OnlyProjectWorkers;
import ua.timetracker.timetrackingserver.service.update.TimeLogUpdater;
import ua.timetracker.timetrackingserver.service.upload.TimeLogUploader;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.restapi.Paths.V1_TIMELOGS;
import static ua.timetracker.shared.util.UserIdUtil.id;

@Slf4j
@RestController
@RequestMapping(value = V1_TIMELOGS, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TimeLogController {

    private static final int BUFFER_SIZE = 1024;

    private final TimeLogsRepository logs;
    private final ProjectsRepository projects;
    private final TimeLogUpdater updater;
    private final TimeLogUploader uploader;
    private final TimeLogImagesService images;
    private final ImageUploadConfig config;
    private final TimeLogImagesRepository imagesRepo;

    @GetMapping(path = "/projects", consumes = MediaType.ALL_VALUE)
    public Flux<ProjectDto> availableProjects(
        @Parameter(hidden = true) Authentication user
    ) {
        log.info("[{}]: get available projects", id(user));
        return projects.timeLoggableProjects(id(user))
            .map(ProjectDto.MAP::map)
            .switchIfEmpty(EntityNotFoundException.mono());
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public Flux<TimeLogDto> uploadedTimeCards(
        @Parameter(hidden = true) Authentication user,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @RequestParam(value = "from", defaultValue = "1970-01-01T00:00") LocalDateTime fromDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @RequestParam(value = "to", defaultValue = "2100-01-01T00:00") LocalDateTime toDate
    ) {
        log.info("[{}]: get uploaded cards {} to {}", id(user), fromDate, toDate);
        val uploads = logs.listUploadedCards(id(user), fromDate, toDate);
        // materialize collections (eager fetch of Projects[])
        return logs.findAllById(uploads).map(TimeLogDto.MAP::map);
    }

    @OnlyProjectWorkers
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TimeLogDto> updateTimeLog(
        @Parameter(hidden = true) Authentication user,
        @PathVariable("id") long cardId,
        @Valid @RequestBody TimeLogCreateOrUpdate updateLog
    ) {
        log.info("[{}]: update card {}, use project: {}, descr.: {}, dur: {}", id(user), cardId, updateLog.getProjectId(), updateLog.getDescription(), updateLog.getDuration());
        return updater.update(id(user), cardId, updateLog)
            .switchIfEmpty(EntityNotFoundException.mono());
    }

    @OnlyProjectWorkers
    @PostMapping(value = "/{id}/increment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TimeLogDto> incrementTimeLog(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("id") long cardId,
            @RequestParam String duration
    ) {
        log.info("[{}]: increment card {}, dur: {}", id(user), cardId, duration);
        return updater.increment(id(user), cardId, Duration.parse(duration)).switchIfEmpty(EntityNotFoundException.mono());
    }

    @OnlyProjectWorkers
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTimeLog(
        @Parameter(hidden = true) Authentication user,
        @PathVariable("id") long cardId
    ) {
        log.info("[{}]: delete log card {}", id(user), cardId);
        return updater.delete(id(user), cardId);
    }

    @OnlyProjectWorkers
    @PutMapping
    public Mono<TimeLogDto> uploadTimelog(
        @Parameter(hidden = true) Authentication user,
        @Valid @RequestBody TimeLogCreateOrUpdate uploadLog) {
        log.info("[{}]: upload card, use project: {}, descr.: {}, dur: {}, tags: {}",
                id(user), uploadLog.getProjectId(), uploadLog.getDescription(), uploadLog.getDuration(), uploadLog.getTags());
        return uploader.upload(id(user), uploadLog)
            .switchIfEmpty(EntityNotFoundException.mono());
    }

    @SneakyThrows
    @PutMapping(path = "/{id}/images/{tag}", consumes = MULTIPART_FORM_DATA_VALUE)
    public Mono<TimeLogImageDto> uploadTimelogImage(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("id") long cardId,
            @PathVariable("tag") String tag,
            @RequestParam(value = "duration", required = false) String duration, // Openapi Codegen does not seem to support Duration
            @RequestParam(value = "timestamp", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp,
            @RequestPart("file") FilePart file) {
        log.info("[{}]: upload time card log, cardId: {}, tag.: {}, dur: {}, time: {}", id(user), cardId, tag, duration, timestamp);
        return images.uploadTimelogImage(id(user), cardId, tag, Duration.parse(duration), timestamp, file);
    }

    @GetMapping(path = "/cards/{time_log_ids}")
    public Flux<TimeLogImageDto> timeCardImagesForTimeLogs(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("time_log_ids") @Valid @NotEmpty Set<@NotNull Long> timeLogIds
    ) {
        log.info("[{}]: get time card log, cardIds: {}", id(user), timeLogIds);
        return imagesRepo.findByUserIdAndCardIds(id(user), timeLogIds).map(TimeLogImageDto.MAP::map);
    }

    @GetMapping("/cards/images/{path}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> downloadTimecardImage(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("path") String path,
            ServerHttpResponse response
    ) {
        log.info("[{}]: download time card log: {}", id(user), path);
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        String[] segments = path.split("/", -1);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + segments[segments.length - 1]);
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return zeroCopyResponse.writeWith(
                DataBufferUtils.read(
                        TimeLogImage.physicalFile(config.getPath(), path), new DefaultDataBufferFactory(), BUFFER_SIZE
                )
        );
    }

    @DeleteMapping("/cards/images/{path}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> deleteTimecardImage(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("path") String path
    ) {
        log.info("[{}]: delete time card log: {}", id(user), path);
        return images.deleteTimecardImage(id(user), path);
    }
}

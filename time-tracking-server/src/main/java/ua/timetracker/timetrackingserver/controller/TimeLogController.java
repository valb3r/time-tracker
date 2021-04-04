package ua.timetracker.timetrackingserver.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.restapi.EntityNotFoundException;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogCreateOrUpdate;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogDto;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogImageDto;
import ua.timetracker.timetrackingserver.service.TimeLogImagesService;
import ua.timetracker.timetrackingserver.service.securityaspect.OnlyProjectWorkers;
import ua.timetracker.timetrackingserver.service.update.TimeLogUpdater;
import ua.timetracker.timetrackingserver.service.upload.TimeLogUploader;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static ua.timetracker.shared.restapi.Paths.V1_TIMELOGS;
import static ua.timetracker.shared.util.UserIdUtil.id;

@RestController
@RequestMapping(value = V1_TIMELOGS, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TimeLogController {

    private final TimeLogsRepository logs;
    private final ProjectsRepository projects;
    private final TimeLogUpdater updater;
    private final TimeLogUploader uploader;
    private final TimeLogImagesService images;

    @GetMapping(path = "/projects", consumes = MediaType.ALL_VALUE)
    public Flux<ProjectDto> availableProjects(
        @Parameter(hidden = true) Authentication user
    ) {
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
        val uploads = logs.listUploadedCards(id(user), fromDate, toDate);
        // materialize collections (eager fetch of Projects[])
        return logs.findAllById(uploads).map(TimeLogDto.MAP::map);
    }

    @OnlyProjectWorkers
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TimeLogDto> updateTimeLog(
        @Parameter(hidden = true) Authentication user,
        @PathVariable("id") long cardId,
        @Valid @RequestBody TimeLogCreateOrUpdate log
    ) {
        return updater.update(id(user), cardId, log)
            .switchIfEmpty(EntityNotFoundException.mono());
    }

    @OnlyProjectWorkers
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTimeLog(
        @Parameter(hidden = true) Authentication user,
        @PathVariable("id") long cardId
    ) {
        return updater.delete(id(user), cardId);
    }

    @OnlyProjectWorkers
    @PutMapping
    public Mono<TimeLogDto> uploadTimelog(
        @Parameter(hidden = true) Authentication user,
        @Valid @RequestBody TimeLogCreateOrUpdate log) {
        return uploader.upload(id(user), log)
            .switchIfEmpty(EntityNotFoundException.mono());
    }

    @PutMapping("/{id}/images/{tag}")
    public Mono<TimeLogImageDto> uploadTimelogImage(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("id") long cardId,
            @PathVariable("tag") String tag,
            @RequestPart("file") FilePart file) {
        return images.uploadTimelogImage(id(user), cardId, tag, file);
    }
}

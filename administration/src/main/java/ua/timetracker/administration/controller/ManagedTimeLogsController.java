package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.config.ImageUploadConfig;
import ua.timetracker.administration.service.securityaspect.ManagedResourceId;
import ua.timetracker.administration.service.securityaspect.OnlyResourceManagers;
import ua.timetracker.shared.persistence.entity.projects.TimeLogImage;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogImagesRepository;
import ua.timetracker.shared.persistence.repository.reactive.TimeLogsRepository;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogDto;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogImageDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.restapi.Paths.V1_MANAGED_TIMELOGS;

@RestController
@RequestMapping(value = V1_MANAGED_TIMELOGS)
@RequiredArgsConstructor
public class ManagedTimeLogsController {

    private static final int BUFFER_SIZE = 1024;

    private final TimeLogsRepository logs;
    private final TimeLogImagesRepository images;
    private final ImageUploadConfig config;

    @OnlyResourceManagers
    @GetMapping(path = "/projects/{project_ids}")
    public Flux<TimeLogDto> timeCardsForManagedProjects(
            @Parameter(hidden = true) Authentication user,
            @ManagedResourceId @PathVariable("project_ids") @Valid @NotEmpty Set<@NotNull Long> projectIds,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "from", defaultValue = "1970-01-01T00:00") LocalDateTime fromDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "to", defaultValue = "2100-01-01T00:00") LocalDateTime toDate
    ) {
        val uploads = logs.listUploadedCards(projectIds, fromDate, toDate);
        // materialize collections (eager fetch of Projects[])
        return logs.findAllById(uploads).map(TimeLogDto.MAP::map);
    }

    @OnlyResourceManagers
    @GetMapping(path = "/projects/{project_ids}/users/{user_ids}")
    public Flux<TimeLogDto> timeCardsForManagedProjectsOfUsers(
            @Parameter(hidden = true) Authentication user,
            @ManagedResourceId @PathVariable("project_ids") @Valid @NotEmpty Set<@NotNull Long> projectIds,
            @ManagedResourceId @PathVariable("user_ids") @Valid @NotEmpty Set<@NotNull Long> userIds,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "from", defaultValue = "1970-01-01T00:00") LocalDateTime fromDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "to", defaultValue = "2100-01-01T00:00") LocalDateTime toDate
    ) {
        val uploads = logs.listUploadedCards(projectIds, userIds, fromDate, toDate);
        // materialize collections (eager fetch of Projects[])
        return logs.findAllById(uploads).map(TimeLogDto.MAP::map);
    }

    @OnlyResourceManagers
    @GetMapping(path = "/projects/{project_ids}/cards/{time_log_ids}")
    public Flux<TimeLogImageDto> timeCardImagesForTimeLogs(
            @Parameter(hidden = true) Authentication user,
            @ManagedResourceId @PathVariable("project_ids") @Valid @NotEmpty Set<@NotNull Long> projectIds,
            @PathVariable("time_log_ids") @Valid @NotEmpty Set<@NotNull Long> timeLogIds
    ) {
        return images.findByProjectsAndCardIds(projectIds, timeLogIds).map(TimeLogImageDto.MAP::map);
    }

    @GetMapping("/cards/images/{path}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> downloadTimecardImage(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("path") String path,
            ServerHttpResponse response
    ) {
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
}

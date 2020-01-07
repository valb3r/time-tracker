package ua.timetracker.timetrackingserver.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.repository.reactive.ProjectsRepository;
import ua.timetracker.shared.restapi.EntityNotFoundException;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogDto;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogUpload;
import ua.timetracker.timetrackingserver.service.securityaspect.CanLogTimeTicket;
import ua.timetracker.timetrackingserver.service.upload.TimeLogUploader;

import javax.validation.Valid;

import static ua.timetracker.shared.restapi.Paths.V1_TIMELOGS;
import static ua.timetracker.shared.util.UserIdUtil.id;

@RestController
@RequestMapping(value = V1_TIMELOGS,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class TimeLogController {

    private final ProjectsRepository projects;
    private final TimeLogUploader uploader;

    @GetMapping(path = "/projects", consumes = MediaType.ALL_VALUE)
    public Flux<ProjectDto> availableProjects(
        @Parameter(hidden = true) Authentication user
    ) {
        return projects.timeLoggableProjects(id(user))
            .map(ProjectDto.MAP::map)
            .switchIfEmpty(EntityNotFoundException.mono());
    }

    @CanLogTimeTicket
    @PutMapping("/{user_id}")
    public Mono<TimeLogDto> uploadTimelog(
        @Parameter(hidden = true) Authentication user,
        @Valid @RequestBody TimeLogUpload log) {
        return uploader.upload(id(user), log)
            .switchIfEmpty(EntityNotFoundException.mono());
    }
}

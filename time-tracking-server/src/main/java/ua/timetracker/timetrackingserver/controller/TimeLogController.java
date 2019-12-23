package ua.timetracker.timetrackingserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.restapi.dto.EntityNotFoundException;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogDto;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogUpload;
import ua.timetracker.timetrackingserver.service.upload.TimeLogUploader;

import javax.validation.Valid;

import static ua.timetracker.shared.restapi.Paths.V1_TIMELOGS;

@RestController
@RequestMapping(value = V1_TIMELOGS,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class TimeLogController {

    private final TimeLogUploader uploader;

    @PutMapping("/{user_id}")
    public Mono<TimeLogDto> uploadTimelog(@PathVariable("user_id") long userId, @Valid @RequestBody TimeLogUpload log) {
        return uploader.upload(userId, log)
                .switchIfEmpty(EntityNotFoundException.mono());
    }
}

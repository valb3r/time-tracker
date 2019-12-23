package ua.timetracker.timetrackingserver.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.restapi.dto.TimeLogUpload;
import ua.timetracker.shared.restapi.dto.TimeLogUploaded;

import javax.validation.Valid;

import static ua.timetracker.shared.restapi.Paths.V1_TIMELOGS;

@RestController
@RequestMapping(V1_TIMELOGS)
public class TimeLogController {

    @PutMapping
    public Mono<TimeLogUploaded> uploadTimelog(@Valid TimeLogUpload log) {
        return null;
    }
}

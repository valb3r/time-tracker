package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.securityaspect.ManagedResourceId;
import ua.timetracker.administration.service.securityaspect.OnlyResourceManagers;
import ua.timetracker.shared.restapi.dto.report.NewReportDto;
import ua.timetracker.shared.restapi.dto.report.ReportDto;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.restapi.Paths.V1_REPORTS;

@RestController
@RequestMapping(value = V1_REPORTS)
@RequiredArgsConstructor
public class ReportController {

    @OnlyResourceManagers
    @Transactional(REACTIVE_TX_MANAGER)
    @PutMapping(path = "/projects/{project_ids}", consumes = APPLICATION_JSON_VALUE)
    public Mono<ReportDto> createReportForProjects(
            @Parameter(hidden = true) Authentication user,
            @ManagedResourceId @PathVariable("project_ids") Set<Long> projectIds,
            @Valid @RequestBody NewReportDto reportDto
    ) {
        return null;
    }

    @OnlyResourceManagers
    @Transactional(REACTIVE_TX_MANAGER)
    @PutMapping(path = "/users/{user_ids}", consumes = APPLICATION_JSON_VALUE)
    public Mono<ReportDto> createReportForUsers(
            @Parameter(hidden = true) Authentication user,
            @ManagedResourceId @PathVariable("user_ids") Set<Long> userIds,
            @Valid @RequestBody NewReportDto reportDto
    ) {
        return null;
    }

    @OnlyResourceManagers
    @GetMapping
    @Transactional(REACTIVE_TX_MANAGER)
    public Flux<ReportDto> ownedReports(@Parameter(hidden = true) Authentication user) {
        return null;
    }

    @GetMapping("/{id}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ReportDto> downloadReport(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("id") long reportId
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ReportDto> deleteReport(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("id") long reportId
    ) {
        return null;
    }
}

package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
import ua.timetracker.administration.service.reports.ReportsService;
import ua.timetracker.administration.service.securityaspect.ManagedResourceId;
import ua.timetracker.administration.service.securityaspect.OnlyResourceManagers;
import ua.timetracker.shared.persistence.entity.report.ReportType;
import ua.timetracker.shared.persistence.repository.reactive.ReportsRepository;
import ua.timetracker.shared.restapi.dto.report.NewReportDto;
import ua.timetracker.shared.restapi.dto.report.ReportDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;
import static ua.timetracker.shared.restapi.Paths.V1_REPORTS;
import static ua.timetracker.shared.util.UserIdUtil.id;

@RestController
@RequestMapping(value = V1_REPORTS)
@RequiredArgsConstructor
public class ReportController {

    private final ReportsRepository reports;
    private final ReportsService reportsSvc;

    @OnlyResourceManagers
    @PutMapping(path = "/projects/{project_ids}", consumes = APPLICATION_JSON_VALUE)
    public Mono<ReportDto> createReportForProjects(
            @Parameter(hidden = true) Authentication user,
            @ManagedResourceId @PathVariable("project_ids") @Valid @NotEmpty Set<@NotNull Long> projectIds,
            @Valid @RequestBody NewReportDto reportDto
    ) {
        return reportsSvc.createReport(id(user), ReportType.BY_PROJECT, projectIds, reportDto);
    }

    @OnlyResourceManagers
    @Transactional(REACTIVE_TX_MANAGER)
    @PutMapping(path = "/users/{user_ids}", consumes = APPLICATION_JSON_VALUE)
    public Mono<ReportDto> createReportForUsers(
            @Parameter(hidden = true) Authentication user,
            @ManagedResourceId @PathVariable("user_ids") @Valid @NotEmpty Set<@NotNull Long> userIds,
            @Valid @RequestBody NewReportDto reportDto
    ) {
        return reportsSvc.createReport(id(user), ReportType.BY_USER, userIds, reportDto);
    }

    @GetMapping
    @Transactional(REACTIVE_TX_MANAGER)
    public Flux<ReportDto> ownedReports(@Parameter(hidden = true) Authentication user) {
        return reports.findAllByOwnerId(id(user)).map(ReportDto.MAP::map);
    }

    @GetMapping("/{id}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> downloadReport(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("id") long reportId,
            ServerHttpResponse response
    ) {
        return reports.findByIdAndOwnerId(id(user), reportId).flatMap(it -> {
            ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
            response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + it.getType().name().toLowerCase() + ".xlsx");
            response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return zeroCopyResponse.writeWith(
                DataBufferUtils.read(
                    new ByteArrayResource(it.getResult()), new DefaultDataBufferFactory(), it.getResult().length
                )
            );
        });
    }

    @DeleteMapping("/{id}")
    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<Void> deleteReport(
            @Parameter(hidden = true) Authentication user,
            @PathVariable("id") long reportId
    ) {
        return reports.findByIdAndOwnerId(id(user), reportId).flatMap(reports::delete);
    }
}

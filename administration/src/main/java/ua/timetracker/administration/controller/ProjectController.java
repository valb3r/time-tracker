package ua.timetracker.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.users.ProjectManager;
import ua.timetracker.shared.restapi.dto.project.ProjectCreate;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;

import javax.validation.Valid;

import static ua.timetracker.shared.restapi.Paths.V1_PROJECTS;

@RestController
@RequestMapping(value = V1_PROJECTS,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectManager manager;

    @PutMapping
    @PreAuthorize("#{auth.canCreateProjects()}")
    public Mono<ProjectDto> createProject(@Valid @RequestBody ProjectCreate projectToCreate) {
        return manager.createProject(projectToCreate);
    }
}

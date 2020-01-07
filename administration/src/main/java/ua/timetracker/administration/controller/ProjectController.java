package ua.timetracker.administration.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.timetracker.administration.service.securityaspect.OnlyResourceManagers;
import ua.timetracker.administration.service.securityaspect.ManagedResourceId;
import ua.timetracker.administration.service.users.ProjectManager;
import ua.timetracker.shared.restapi.dto.project.ProjectCreateOrUpdate;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ua.timetracker.shared.restapi.Paths.V1_PROJECTS;

@RestController
@RequestMapping(value = V1_PROJECTS)
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectManager manager;

    @OnlyResourceManagers
    @PutMapping(path = "/of_group/{parent_group_id}", consumes = APPLICATION_JSON_VALUE)
    public Mono<ProjectDto> createProject(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("parent_group_id") long parentGroupId,
        @Valid @RequestBody ProjectCreateOrUpdate projectToCreate
    ) {
        return manager.createProject(parentGroupId, projectToCreate);
    }

    @OnlyResourceManagers
    @PostMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public Mono<ProjectDto> updateProject(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("id") long projectId,
        @RequestBody ProjectCreateOrUpdate update
    ) {
        return manager.updateProject(projectId, update);
    }

    @OnlyResourceManagers
    @DeleteMapping(path = "/{id}")
    public Mono<Void> deleteProject(
        @Parameter(hidden = true) Authentication user,
        @ManagedResourceId @PathVariable("id") long projectToDelete
    ) {
        return manager.deleteProject(projectToDelete);
    }
}

package ua.timetracker.shared.restapi.dto.project;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.Project;

import java.util.Set;

@Data
public class ProjectDto {

    public static final ProjectDto.FromEntity MAP = Mappers.getMapper(ProjectDto.FromEntity.class);

    private Long id;
    private String name;
    private String code;
    private String description;
    private Set<String> activities;
    // screenshots
    private boolean screenshots;
    private Float quality;
    private Long intervalminutes;

    @Mapper
    public interface FromEntity {

        ProjectDto map(Project source, @MappingTarget ProjectDto target);

        default ProjectDto map(Project project) {
            ProjectDto mapped = map(project, new ProjectDto());
            mapped.setIntervalminutes(null == project.getInterval() ? 0 : project.getInterval().toMinutes());
            return mapped;
        }
    }
}

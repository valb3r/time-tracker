package ua.timetracker.shared.restapi.dto.project;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.Project;

public class ProjectDto {

    public static final ProjectDto.FromEntity MAP = Mappers.getMapper(ProjectDto.FromEntity.class);

    @Mapper
    public interface FromEntity {
        ProjectDto map(Project project);
    }
}

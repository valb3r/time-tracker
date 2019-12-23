package ua.timetracker.shared.restapi.dto.project;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.Project;

@Data
public class ProjectDto {

    public static final ProjectDto.FromEntity MAP = Mappers.getMapper(ProjectDto.FromEntity.class);

    private Long id;
    private String name;
    private String code;
    private String description;

    @Mapper
    public interface FromEntity {
        ProjectDto map(Project project);
    }
}

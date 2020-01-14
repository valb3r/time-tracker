package ua.timetracker.shared.restapi.dto.timelog;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.Location;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
public class TimeLogDto {

    public static final TimeLogDto.FromEntity MAP = Mappers.getMapper(TimeLogDto.FromEntity.class);

    private Long id;

    private Collection<String> tags;
    private Duration duration;
    private String description;
    private Location location;
    private Set<ProjectDto> projects;
    private LocalDateTime timestamp;

    @Mapper
    public interface FromEntity {
        TimeLogDto map(TimeLog log);
    }
}

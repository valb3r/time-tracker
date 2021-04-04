package ua.timetracker.shared.restapi.dto.timelog;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
public class TimeLogDto {

    public static final TimeLogDto.FromEntity MAP = Mappers.getMapper(TimeLogDto.FromEntity.class);

    private Long id;

    private Long projectid; // should be replaced by projects field
    private Collection<String> tags;
    private long durationminutes;

    @NotNull
    @JsonFormat(shape = STRING)
    @Schema(type = "string", format = "duration", example = "PT1S")
    private Duration duration;

    private String description;
    private String location;
    private Set<ProjectDto> projects;

    @Schema(type = "string" , format = "date-time")
    private LocalDateTime timestamp;

    private Long userid;
    private String username;

    @Mapper
    public interface FromEntity {
        TimeLogDto map(TimeLog source, @MappingTarget TimeLogDto target);

        default TimeLogDto map(TimeLog source) {
            TimeLogDto target = new TimeLogDto();
            target.setDurationminutes(source.getDuration().toMinutes());
            if (null != source.getUser()) {
                target.setUserid(source.getUser().getId());
                target.setUsername(source.getUser().getUsername());
            }

            return map(source, target);
        }
    }
}

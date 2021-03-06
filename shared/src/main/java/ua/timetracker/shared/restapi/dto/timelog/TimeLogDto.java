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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
public class TimeLogDto {

    public static final TimeLogDto.FromEntity MAP = Mappers.getMapper(TimeLogDto.FromEntity.class);

    private Long id;

    private Long projectid; // should be replaced by projects field
    private Collection<String> tags;
    private long durationminutes;
    private long durationseconds; // is informal

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
    private String userfullname;
    private Map<String, Long> incrementtagsminutes;

    @Mapper
    public interface FromEntity {
        TimeLogDto map(TimeLog source, @MappingTarget TimeLogDto target);

        default TimeLogDto map(TimeLog source) {
            TimeLogDto target = new TimeLogDto();
            target.setDurationminutes(source.getDuration().toMinutes());
            target.setDurationseconds(source.getDuration().getSeconds());
            if (null != source.getUser()) {
                target.setUserid(source.getUser().getId());
                target.setUsername(source.getUser().getUsername());
                target.setUserfullname(source.getUser().getFullname());
            }
            target.setIncrementtagsminutes(source.parseIncrementTags().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().toMinutes())));

            return map(source, target);
        }
    }
}

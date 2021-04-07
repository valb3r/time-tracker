package ua.timetracker.shared.restapi.dto.timelog;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.TimeLogImage;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
public class TimeLogImageDto {

    public static final TimeLogImageDto.FromEntity MAP = Mappers.getMapper(TimeLogImageDto.FromEntity.class);

    private Long id;
    private String imageurl;

    @JsonFormat(shape = STRING)
    @Schema(type = "string", format = "duration", example = "PT1S")
    private Duration duration;

    @Schema(type = "string" , format = "date-time")
    private LocalDateTime timestamp;

    private long durationseconds;
    private long durationminutes;

    @Mapper
    public interface FromEntity {

        @Mapping(source = "imageUrl", target = "imageurl")
        default TimeLogImageDto map(TimeLogImage source) {
            val target = new TimeLogImageDto();
            merge(source, target);
            target.setDurationseconds(null == target.getDuration() ? 0L : target.getDuration().getSeconds());
            target.setDurationminutes(null == target.getDuration() ? 0L : target.getDuration().toMinutes());
            return target;
        }

        @Mapping(source = "imageUrl", target = "imageurl")
        void merge(TimeLogImage source, @MappingTarget TimeLogImageDto target);
    }
}

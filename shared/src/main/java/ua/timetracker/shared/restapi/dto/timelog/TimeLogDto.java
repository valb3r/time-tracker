package ua.timetracker.shared.restapi.dto.timelog;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;

import java.time.LocalDateTime;

@Data
public class TimeLogDto {

    public static final TimeLogDto.FromEntity MAP = Mappers.getMapper(TimeLogDto.FromEntity.class);

    private Long id;
    private LocalDateTime loggedAt;

    @Mapper
    public interface FromEntity {
        TimeLogDto map(TimeLog log);
    }
}

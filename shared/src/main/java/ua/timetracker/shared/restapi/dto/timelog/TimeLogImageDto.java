package ua.timetracker.shared.restapi.dto.timelog;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.TimeLogImage;

@Data
public class TimeLogImageDto {

    public static final TimeLogImageDto.FromEntity MAP = Mappers.getMapper(TimeLogImageDto.FromEntity.class);

    private Long id;
    private String imageurl;

    @Mapper
    public interface FromEntity {

        @Mapping(source = "imageUrl", target = "imageurl")
        TimeLogImageDto map(TimeLogImage source);
    }
}

package ua.timetracker.shared.restapi.dto.timelog;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.Location;

@Data
public class LocationDto {

    public static final LocationDto.FromEntity FROM = Mappers.getMapper(LocationDto.FromEntity.class);
    public static final LocationDto.FromEntity TO = Mappers.getMapper(LocationDto.FromEntity.class);

    private Long id;
    private Location.KnownLocations code;
    private String description;

    @Mapper
    public interface FromEntity {

        LocationDto mapFrom(Location location);
        Location mapTo(LocationDto location);
    }
}

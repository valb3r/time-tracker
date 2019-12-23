package ua.timetracker.shared.restapi.dto.timelog;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.projects.Location;

@Data
public class LocationUpload {

    public static final LocationUpload.FromEntity MAP = Mappers.getMapper(LocationUpload.FromEntity.class);

    private Location.KnownLocations code;
    private String description;

    @Mapper
    public interface FromEntity {
        Location map(LocationUpload location);
    }
}

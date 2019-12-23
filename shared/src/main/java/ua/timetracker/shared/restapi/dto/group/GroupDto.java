package ua.timetracker.shared.restapi.dto.group;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.groups.Group;

@Data
public class GroupDto {

    public static final GroupDto.FromEntity MAP = Mappers.getMapper(GroupDto.FromEntity.class);

    private Long id;
    private String name;

    @Mapper
    public interface FromEntity {
        GroupDto map(Group group);
    }
}

package ua.timetracker.shared.restapi.dto.group;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.groups.Group;

public class GroupDto {

    public static final GroupDto.FromEntity MAP = Mappers.getMapper(GroupDto.FromEntity.class);

    @Mapper
    public interface FromEntity {
        GroupDto map(Group group);
    }
}

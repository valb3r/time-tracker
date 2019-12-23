package ua.timetracker.shared.restapi.dto.role;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.roles.Role;

public class RoleDto {

    public static final RoleDto.FromEntity MAP = Mappers.getMapper(RoleDto.FromEntity.class);

    @Mapper
    public interface FromEntity {
        RoleDto map(Role role);
    }
}

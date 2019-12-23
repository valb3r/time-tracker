package ua.timetracker.shared.restapi.dto.role;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.roles.Role;

@Data
public class RoleDto {

    public static final RoleDto.FromEntity MAP = Mappers.getMapper(RoleDto.FromEntity.class);

    private Long id;
    private String name;

    @Mapper
    public interface FromEntity {
        RoleDto map(Role role);
    }
}

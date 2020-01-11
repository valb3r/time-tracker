package ua.timetracker.shared.restapi.dto.group;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.groups.Group;
import ua.timetracker.shared.restapi.dto.project.ProjectDto;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import java.util.Set;

@Data
public class GroupDto {

    public static final GroupDto.FromEntity MAP = Mappers.getMapper(GroupDto.FromEntity.class);

    private Long id;
    private String name;

   // private Set<GroupDto> children;
    private Set<UserDto> users;
    private Set<ProjectDto> projects;

    @Mapper
    public interface FromEntity {
        GroupDto map(Group group);
    }
}

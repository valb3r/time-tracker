package ua.timetracker.shared.restapi.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.timetracker.shared.restapi.dto.group.GroupDto;
import ua.timetracker.shared.restapi.dto.user.UserDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectActorDto {

    private UserDto user;
    private GroupDto source;
}

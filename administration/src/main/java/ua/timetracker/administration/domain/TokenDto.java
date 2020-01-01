package ua.timetracker.administration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.timetracker.shared.restapi.dto.user.UserDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

    private UserDto user;
    private String issuedToken;
}

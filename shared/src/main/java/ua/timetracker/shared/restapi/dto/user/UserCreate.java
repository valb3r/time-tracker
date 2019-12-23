package ua.timetracker.shared.restapi.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserCreate {

    @NotBlank
    private String username;
}

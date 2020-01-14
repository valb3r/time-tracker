package ua.timetracker.shared.restapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreate {

    @NotBlank
    private String username;

    @NotBlank
    private String fullname;

    @NotBlank
    private String password;
}

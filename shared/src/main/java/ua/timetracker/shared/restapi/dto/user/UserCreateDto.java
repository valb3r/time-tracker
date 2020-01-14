package ua.timetracker.shared.restapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank
    private String username;

    private String fullname;

    @NotBlank
    private String password;

    @NotNull
    private String rate;
}

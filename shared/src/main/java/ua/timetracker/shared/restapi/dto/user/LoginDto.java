package ua.timetracker.shared.restapi.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Long clientversion; // Optional client version indicator
}

package ua.timetracker.shared.restapi.dto.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleCreate {

    @NotBlank
    private String name;
}

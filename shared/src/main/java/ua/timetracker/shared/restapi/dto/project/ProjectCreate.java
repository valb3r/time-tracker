package ua.timetracker.shared.restapi.dto.project;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectCreate {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    private String description;
}

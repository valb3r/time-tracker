package ua.timetracker.shared.restapi.dto.project;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class ProjectCreateOrUpdate {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotEmpty
    private Set<@NotBlank String> activities;

    private String description;
}

package ua.timetracker.shared.restapi.dto.group;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupCreateOrUpdateDto {

    @NotBlank
    private String name;
}

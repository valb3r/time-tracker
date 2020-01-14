package ua.timetracker.shared.restapi.dto.role;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class RoleDetailsDto {

    @NotNull
    private String rate;

    @NotNull
    private LocalDateTime from;

    @NotNull
    @FutureOrPresent
    private LocalDateTime to;
}

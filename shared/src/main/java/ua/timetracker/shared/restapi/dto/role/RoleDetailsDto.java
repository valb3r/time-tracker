package ua.timetracker.shared.restapi.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDetailsDto {

    @NotNull
    private String rate;

    @NotNull
    private LocalDateTime from;

    @NotNull
    @FutureOrPresent
    private LocalDateTime to;
}

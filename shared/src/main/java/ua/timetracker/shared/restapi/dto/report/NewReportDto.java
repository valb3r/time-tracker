package ua.timetracker.shared.restapi.dto.report;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class NewReportDto {

    @NotNull
    private Long templateId;

    @NotNull
    private LocalDateTime from;

    @NotNull
    private LocalDateTime to;
}

package ua.timetracker.shared.restapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
public class TimeLogUpload {

    @NotNull
    private Long projectId;

    @NotEmpty
    private Collection<String> tags;

    @NotNull
    @JsonFormat(shape = STRING)
    @Schema(type = "string", format = "duration", example = "PT1S")
    private Duration duration;
}

package ua.timetracker.shared.restapi.dto.timelog;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Setter
@NoArgsConstructor
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

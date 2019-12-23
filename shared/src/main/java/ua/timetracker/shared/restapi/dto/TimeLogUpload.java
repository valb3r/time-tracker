package ua.timetracker.shared.restapi.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Collection;

@Data
public class TimeLogUpload {

    @NotNull
    private Long projectId;

    @NotEmpty
    private Collection<String> tags;

    @NotNull
    private Duration duration;
}

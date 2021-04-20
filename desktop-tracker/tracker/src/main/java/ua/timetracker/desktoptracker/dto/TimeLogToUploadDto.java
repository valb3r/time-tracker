package ua.timetracker.desktoptracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.timetracker.desktoptracker.api.tracker.model.ProjectDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLogToUploadDto {

    private LocalDateTime forTime;
    private long loggedDuration;
    private ProjectDto project;
    private String taskMessage;
    private String taskTag;
    private String screenShotState;
}

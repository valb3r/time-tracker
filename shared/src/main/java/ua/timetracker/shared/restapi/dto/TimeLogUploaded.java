package ua.timetracker.shared.restapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeLogUploaded {

    private LocalDateTime uploadedAt;
}

package ua.timetracker.shared.restapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.timetracker.shared.persistence.entity.TimeLog;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TimeLogUploaded {

    private LocalDateTime uploadedAt;

    public TimeLogUploaded(TimeLog log) {
        this.uploadedAt = log.getLoggedAtUtc();
    }
}

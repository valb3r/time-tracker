package ua.timetracker.shared.restapi.dto.timelog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.timetracker.shared.persistence.entity.projects.TimeLog;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TimeLogUploaded {

    private Long id;
    private LocalDateTime uploadedAt;

    public TimeLogUploaded(TimeLog log) {
        this.uploadedAt = log.getLoggedAt();
    }
}

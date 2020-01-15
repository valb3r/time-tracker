package ua.timetracker.shared.restapi.dto.report;

import lombok.Data;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ReportDto {

    private long id;

    private String job;
    private UserDto owner;
    private LocalDateTime created;

    private LocalDateTime from;
    private LocalDateTime to;
    private Set<Long> targets;
}

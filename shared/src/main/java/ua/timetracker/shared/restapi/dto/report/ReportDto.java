package ua.timetracker.shared.restapi.dto.report;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.report.Report;
import ua.timetracker.shared.persistence.entity.report.ReportStatus;
import ua.timetracker.shared.persistence.entity.report.ReportType;
import ua.timetracker.shared.restapi.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ReportDto {

    public static final ReportDto.FromEntity MAP = Mappers.getMapper(ReportDto.FromEntity.class);

    private long id;

    private ReportType type;

    private String job;
    private UserDto owner;
    private LocalDateTime created;

    private LocalDateTime from;
    private LocalDateTime to;
    private Set<Long> targets;

    private ReportStatus status;

    @Mapper
    public interface FromEntity {
        ReportDto map(Report report);
    }
}

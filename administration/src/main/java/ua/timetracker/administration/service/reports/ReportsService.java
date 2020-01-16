package ua.timetracker.administration.service.reports;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ua.timetracker.shared.persistence.entity.report.Report;
import ua.timetracker.shared.persistence.entity.report.ReportType;
import ua.timetracker.shared.persistence.repository.reactive.ReportTemplatesRepository;
import ua.timetracker.shared.persistence.repository.reactive.ReportsRepository;
import ua.timetracker.shared.persistence.repository.reactive.UsersRepository;
import ua.timetracker.shared.restapi.dto.report.NewReportDto;
import ua.timetracker.shared.restapi.dto.report.ReportDto;

import java.util.HashSet;
import java.util.Set;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service
@RequiredArgsConstructor
public class ReportsService {

    private final ReportTemplatesRepository templates;
    private final UsersRepository users;
    private final ReportsRepository reports;

    @Transactional(REACTIVE_TX_MANAGER)
    public Mono<ReportDto> createReport(long ownerId, ReportType type, Set<Long> projectIds, NewReportDto reportDto) {
        val newReport = Report.MAP.map(reportDto);
        newReport.setTargets(new HashSet<>(projectIds));
        newReport.setType(type);

        val user = users.findById(ownerId);
        val template = templates.findById(reportDto.getTemplateid());

        return user
            .zipWith(template, (usr, tmplt) -> {
                newReport.setOwner(usr);
                newReport.setTemplate(tmplt);
                return newReport;
            }).flatMap(report -> reports.save(report).map(ReportDto.MAP::map));
    }
}

package ua.timetracker.shared.restapi.dto.report;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.report.ReportTemplate;

@Data
public class ReportTemplateDto {

    public static final ReportTemplateDto.FromEntity MAP = Mappers.getMapper(ReportTemplateDto.FromEntity.class);

    private long id;
    private String description;

    @Mapper
    public interface FromEntity {
        ReportTemplateDto map(ReportTemplate report);
    }
}

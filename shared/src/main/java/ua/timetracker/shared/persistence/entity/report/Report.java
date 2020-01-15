package ua.timetracker.shared.persistence.entity.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.restapi.dto.report.NewReportDto;

import java.time.LocalDateTime;
import java.util.Set;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.INCOMING;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNS;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@Node
@AllArgsConstructor
public class Report {

    public static final Report.FromDto MAP = Mappers.getMapper(Report.FromDto.class);

    @Id
    @GeneratedValue
    private Long id;

    private String job;

    private LocalDateTime from;
    private LocalDateTime to;
    private Set<Long> targets;

    @Relationship(type = OWNS, direction = INCOMING)
    private User owner;

    @Mapper
    public interface FromDto {
        Report map(NewReportDto newReport);
    }
}

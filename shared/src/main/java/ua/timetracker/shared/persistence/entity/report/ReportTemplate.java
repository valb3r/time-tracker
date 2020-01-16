package ua.timetracker.shared.persistence.entity.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@Node
@AllArgsConstructor
public class ReportTemplate {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private byte[] template;

    private ReportStatus status = ReportStatus.SCHEDULED;
}

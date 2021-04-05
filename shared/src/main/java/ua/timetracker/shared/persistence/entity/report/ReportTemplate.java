package ua.timetracker.shared.persistence.entity.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@Node
@NodeEntity
@AllArgsConstructor
public class ReportTemplate {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    // BASE64 as OGM-SDN mapping has impedance when trying to map byte to string
    private String template;
}

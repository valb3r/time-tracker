package ua.timetracker.shared.persistence.entity.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String description;

    public enum KnownLocations {

        OFFICE,
        HOME_OFFICE,
        ON_CLIENT_SITE,
        EN_ROUTE,
        REMOTE_FIRST,
        UNKNOWN
    }
}

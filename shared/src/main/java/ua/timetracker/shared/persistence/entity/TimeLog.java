package ua.timetracker.shared.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.CreatedDate;
import ua.timetracker.shared.restapi.dto.TimeLogUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;
import static ua.timetracker.shared.persistence.entity.Relationships.LOGGED_FOR;
import static ua.timetracker.shared.persistence.entity.Relationships.OWNER;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class TimeLog {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private Collection<String> tags;

    @NotNull
    private Duration duration;

    @NotBlank
    private String description;

    @Relationship(type = OWNER, direction = OUTGOING)
    private User user;

    @Relationship(type = LOGGED_FOR, direction = OUTGOING)
    private Set<Project> projects;

    @CreatedDate
    private LocalDateTime loggedAtUtc;

    public TimeLog(TimeLogUpload upload) {
        tags = upload.getTags();
        duration = upload.getDuration();
    }
}

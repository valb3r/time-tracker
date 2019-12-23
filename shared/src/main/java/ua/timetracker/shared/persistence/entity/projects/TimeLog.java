package ua.timetracker.shared.persistence.entity.projects;

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
import org.springframework.data.annotation.CreatedDate;
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogUpload;

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

    public static final TimeLog.FromDto MAP = Mappers.getMapper(TimeLog.FromDto.class);

    @Id
    @GeneratedValue
    private Long id;

    private Collection<String> tags;
    private Duration duration;
    private String description;
    private Location location;

    @Relationship(type = OWNER, direction = OUTGOING)
    private User user;

    @Relationship(type = LOGGED_FOR, direction = OUTGOING)
    private Set<Project> projects;

    @CreatedDate
    private LocalDateTime loggedAt;

    @Mapper
    public interface FromDto {
        TimeLog map(TimeLogUpload log);
    }
}

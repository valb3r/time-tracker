package ua.timetracker.shared.persistence.entity.projects;

import com.google.common.collect.Iterables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.restapi.dto.timelog.TimeLogCreateOrUpdate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;
import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.LOGGED_FOR;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNER;

@Getter
@Setter
@Node
@NodeEntity
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class TimeLog {

    public static final TimeLog.FromDto MAP = Mappers.getMapper(TimeLog.FromDto.class);
    public static final TimeLog.Merge UPDATE = Mappers.getMapper(TimeLog.Merge.class);

    @Id
    @GeneratedValue
    private Long id;

    private Collection<String> tags;
    private Duration duration;
    private String description;
    private String location;

    @Relationship(type = OWNER, direction = OUTGOING)
    private User user;

    @Relationship(type = LOGGED_FOR, direction = OUTGOING)
    private Set<Project> projects;

    private LocalDateTime timestamp;

    private Set<String> incrementTags;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // to be replaced by multi-projects
    public Long getProjectid() {
        return null == projects ? null : Iterables.getFirst(projects, new Project()).getId();
    }

    @Mapper
    public interface FromDto {
        TimeLog map(TimeLogCreateOrUpdate log);
    }

    @Mapper(nullValuePropertyMappingStrategy = IGNORE)
    public interface Merge {
        void update(TimeLogCreateOrUpdate updated, @MappingTarget TimeLog result);
    }

    public Map<String, Duration> parseIncrementTags() {
        return null == this.getIncrementTags() ? new HashMap<>() :
                this.getIncrementTags().stream().collect(Collectors.toMap(it -> it.split(":")[0], it -> Duration.parse(it.split(":")[1])));
    }
}

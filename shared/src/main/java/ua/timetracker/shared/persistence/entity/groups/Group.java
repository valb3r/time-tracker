package ua.timetracker.shared.persistence.entity.groups;

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
import ua.timetracker.shared.persistence.entity.projects.Project;
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.restapi.dto.group.GroupCreate;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.INCOMING;
import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.IN_GROUP;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.OWNS;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@Node
@AllArgsConstructor
public class Group {

    public static final Group.FromDto MAP = Mappers.getMapper(Group.FromDto.class);

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // Resource pool

    // Same entity mapping seem to be unstable:
    // Issue-causing query:
    // MATCH (startNode)-[rel:`HAS_CHILD`]->(:`Group`) WHERE id(startNode) = $fromId DELETE rel
    // on parent-child relationship
    // @Relationship(type = IN, direction = INCOMING)
    // private Set<Group> children;
    // So that ^^ above won't work.

    @Relationship(type = IN_GROUP, direction = INCOMING)
    private Set<User> users = new HashSet<>();

    @Relationship(type = OWNS, direction = OUTGOING)
    private Set<Project> projects = new HashSet<>();

    @Mapper
    public interface FromDto {
        Group map(GroupCreate group);
    }
}

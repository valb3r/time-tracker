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
import ua.timetracker.shared.persistence.entity.user.User;
import ua.timetracker.shared.restapi.dto.group.GroupCreate;

import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.INCOMING;
import static ua.timetracker.shared.persistence.entity.Relationships.IN;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Group {

    public static final Group.FromDto MAP = Mappers.getMapper(Group.FromDto.class);

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = IN, direction = INCOMING)
    private List<Group> children;

    @Relationship(type = IN, direction = INCOMING)
    private List<User> users;

    @Mapper
    public interface FromDto {
        Group map(GroupCreate group);
    }
}

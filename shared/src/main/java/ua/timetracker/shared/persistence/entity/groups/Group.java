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

import java.util.LinkedHashSet;
import java.util.Set;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.INCOMING;
import static ua.timetracker.shared.persistence.entity.realationships.Relationships.IN;

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

    // Resource pool:
    @Relationship(type = IN, direction = INCOMING)
    private Set<Group> children;

    @Relationship(type = IN, direction = INCOMING)
    private Set<User> users;

    @Relationship(type = IN, direction = INCOMING)
    private Set<Project> projects;

    public Set<User> getUsersAndInitialize() {
        this.users = null == this.users ? new LinkedHashSet<>() : this.users;
        return this.users;
    }

    public Set<Project> getProjectsAndInitialize() {
        this.projects = null == this.projects ? new LinkedHashSet<>() : this.projects;
        return this.projects;
    }

    public Set<Group> getChildrenAndInitialize() {
        this.children = null == this.children ? new LinkedHashSet<>() : this.children;
        return this.children;
    }

    @Mapper
    public interface FromDto {
        Group map(GroupCreate group);
    }
}

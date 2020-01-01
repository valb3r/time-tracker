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
import ua.timetracker.shared.restapi.dto.project.ProjectCreate;

import java.util.List;

import static ua.timetracker.shared.persistence.entity.Relationships.DEVELOPER;
import static ua.timetracker.shared.persistence.entity.Relationships.MANAGER;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Project {

    public static final Project.FromDto MAP = Mappers.getMapper(Project.FromDto.class);

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String code;
    private String description;

    @Relationship(value = DEVELOPER, direction = Relationship.Direction.INCOMING)
    private List<Object> developers;

    @Relationship(value = MANAGER, direction = Relationship.Direction.INCOMING)
    private List<Object> managers;

    @Mapper
    public interface FromDto {
        Project map(ProjectCreate project);
    }
}

package ua.timetracker.shared.persistence.entity.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import ua.timetracker.shared.restapi.dto.project.ProjectCreateOrUpdate;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Getter
@Setter
@Node
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Project {

    public static final Project.FromDto MAP = Mappers.getMapper(Project.FromDto.class);
    public static final Project.Merge UPDATE = Mappers.getMapper(Project.Merge.class);

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String code;
    private String description;

    // SDN / RX Neo4J currently does not work properly with abstract relationship classes. Omitting them and using queries.

    @Mapper
    public interface FromDto {
        Project map(ProjectCreateOrUpdate project);
    }

    @Mapper(nullValuePropertyMappingStrategy = IGNORE)
    public interface Merge {
        void update(ProjectCreateOrUpdate updated, @MappingTarget Project result);
    }

}

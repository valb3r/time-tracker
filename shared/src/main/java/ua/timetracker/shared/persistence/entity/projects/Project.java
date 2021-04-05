package ua.timetracker.shared.persistence.entity.projects;

import lombok.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import ua.timetracker.shared.restapi.dto.project.ProjectCreateOrUpdate;

import java.util.Set;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Getter
@Setter
@Node
@NodeEntity
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
    private Set<String> activities;
    // screenshot related
    private Boolean screenshots;
    private Double quality;
    // FIXME java.lang.ClassCastException: Cannot cast org.neo4j.driver.internal.InternalIsoDuration to java.time.Duration
    private Long intervalM;

    // SDN / RX Neo4J currently does not work properly with abstract relationship classes. Omitting them and using queries.

    @Mapper
    public interface FromDto {
        Project map(ProjectCreateOrUpdate project);
    }

    @Mapper(nullValuePropertyMappingStrategy = IGNORE)
    public interface Merge {
        default void update(ProjectCreateOrUpdate updated, Project result) {
            doUpdate(updated, result);
            if (null != updated.getIntervalminutes()) {
                result.setIntervalM(updated.getIntervalminutes());
            }
        }

        void doUpdate(ProjectCreateOrUpdate updated, @MappingTarget Project result);
    }

}

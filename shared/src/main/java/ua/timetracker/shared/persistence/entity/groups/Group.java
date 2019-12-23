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
import ua.timetracker.shared.restapi.dto.group.GroupCreate;

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

    @Mapper
    public interface FromDto {
        Group map(GroupCreate group);
    }
}

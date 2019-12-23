package ua.timetracker.shared.persistence.entity.roles;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;

@Getter
@Setter
@Node
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
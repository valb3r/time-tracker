package ua.timetracker.shared.persistence.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ua.timetracker.shared.restapi.dto.user.UserCreate;

import java.time.LocalDateTime;

@Getter
@Setter
@Node
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modified;

    @LastModifiedBy
    private String modifiedBy;

    public User(UserCreate toCreate) {
        this.name = toCreate.getUsername();
    }
}

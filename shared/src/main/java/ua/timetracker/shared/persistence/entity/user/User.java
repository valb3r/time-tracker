package ua.timetracker.shared.persistence.entity.user;

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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ua.timetracker.shared.restapi.dto.user.UserCreate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@Node
@AllArgsConstructor
public class User {

    public static final User.FromDto MAP = Mappers.getMapper(User.FromDto.class);

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String fullname;

    private String encodedPassword;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modified;

    @LastModifiedBy
    private String modifiedBy;

    public User(UserCreate toCreate) {
        this.name = toCreate.getUsername();
    }

    @Mapper
    public interface FromDto {
        User map(UserCreate user);
    }
}

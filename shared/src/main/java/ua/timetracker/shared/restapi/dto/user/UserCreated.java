package ua.timetracker.shared.restapi.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.timetracker.shared.persistence.entity.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserCreated {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public UserCreated(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt();
    }
}

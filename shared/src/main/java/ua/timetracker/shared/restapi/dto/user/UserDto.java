package ua.timetracker.shared.restapi.dto.user;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserDto {

    public static final UserDto.FromEntity MAP = Mappers.getMapper(UserDto.FromEntity.class);

    private Long id;
    private String username;
    private String fullname;
    private LocalDateTime createdAt;
    private BigDecimal rate;

    @Mapper
    public interface FromEntity {
        UserDto map(User user);
    }
}

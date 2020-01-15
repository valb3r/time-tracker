package ua.timetracker.shared.restapi.dto.user;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.timetracker.shared.persistence.entity.user.User;

import java.time.LocalDateTime;

@Data
public class UserWithoutRateDto {

    public static final UserWithoutRateDto.FromEntity MAP = Mappers.getMapper(UserWithoutRateDto.FromEntity.class);

    private Long id;
    private String username;
    private String fullname;
    private LocalDateTime createdAt;

    @Mapper
    public interface FromEntity {
        UserWithoutRateDto map(User user);
    }
}

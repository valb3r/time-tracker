package ua.timetracker.shared.restapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserUpdateDto {

    public static final SimpleUserUpdateDto.To MAP = Mappers.getMapper(SimpleUserUpdateDto.To.class);

    @NotBlank
    private String username;

    private String fullname;

    @Mapper
    public interface To {
        UserUpdateDto map(SimpleUserUpdateDto from);
    }
}

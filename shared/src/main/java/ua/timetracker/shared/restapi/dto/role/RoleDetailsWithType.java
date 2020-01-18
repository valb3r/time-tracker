package ua.timetracker.shared.restapi.dto.role;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoleDetailsWithType extends RoleDetailsDto {

    private String type;

    public RoleDetailsWithType(String rate, LocalDateTime from, LocalDateTime to, String type) {
        super(rate, from, to);
        this.type = type;
    }
}

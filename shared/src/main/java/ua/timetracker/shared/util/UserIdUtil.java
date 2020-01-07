package ua.timetracker.shared.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;

@UtilityClass
public class UserIdUtil {

    public long id(Authentication auth) {
        return Long.parseLong(auth.getName());
    }
}

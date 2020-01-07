package ua.timetracker.timetrackingserver.service.securityaspect;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyProjectWorkers {
}

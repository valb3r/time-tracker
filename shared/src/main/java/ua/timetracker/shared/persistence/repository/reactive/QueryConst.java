package ua.timetracker.shared.persistence.repository.reactive;

import lombok.experimental.UtilityClass;

import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.DEVELOPER_ROLE;
import static ua.timetracker.shared.persistence.entity.realationships.ProjectRole.Constants.MANAGER_ROLE;

@UtilityClass
public class QueryConst {

    public static final String DATE_VALID_FILTER_ON_DEV_OR_MGR_ROLE =
        "AND NONE(dated in role WHERE (type(dated) IN ['"+ MANAGER_ROLE + "', '" + DEVELOPER_ROLE + "']) AND (localdatetime() <= dated.from OR localdatetime() >= dated.to))";
}

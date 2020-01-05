package ua.timetracker.shared.persistence.entity.realationships;

import lombok.experimental.UtilityClass;

public enum ProjectRole {

    MANAGER(Constants.MANAGER_ROLE),
    DEVELOPER(Constants.DEVELOPER_ROLE);

    ProjectRole(String name) {
    }

    @UtilityClass
    public static class Constants {

        public static final String DEVELOPER_ROLE = "DEVELOPER";
        public static final String MANAGER_ROLE = "MANAGER";
    }
}

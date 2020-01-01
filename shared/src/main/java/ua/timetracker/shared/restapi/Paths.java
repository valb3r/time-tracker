package ua.timetracker.shared.restapi;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Paths {

    public static final String V1 = "/v1";
    public static final String V1_TIMELOGS = V1 + "/timelogs";
    public static final String V1_PROJECTS = V1 + "/projects";
    public static final String V1_ROLES = V1 + "/roles";
    public static final String V1_USERS = V1 + "/users";
    public static final String V1_GROUPS = V1 + "/groups";
    public static final String V1_LOGIN = V1 + "/login";
}

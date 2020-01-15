package ua.timetracker.shared.restapi;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Paths {

    public static final String V1 = "/v1";
    public static final String V1_RESOURCES = "/v1/resources";
    public static final String V1_TIMELOGS = V1_RESOURCES + "/timelogs";
    public static final String V1_PROJECTS = V1_RESOURCES + "/projects";
    public static final String V1_REPORTS = V1_RESOURCES + "/reports";
    public static final String V1_ROLES = V1_RESOURCES + "/roles";
    public static final String V1_USERS = V1_RESOURCES + "/users";
    public static final String V1_GROUPS = V1_RESOURCES + "/groups";
    public static final String V1_OWNED = V1_RESOURCES + "/owned-resources";
    public static final String V1_LOGIN = V1 + "/login";
}

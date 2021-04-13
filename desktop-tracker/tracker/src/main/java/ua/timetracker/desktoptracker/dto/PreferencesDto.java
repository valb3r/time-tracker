package ua.timetracker.desktoptracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesDto {

    private String username = "";
    private String apiUrl = "https://demo.timetracker.tk";
    private int width = 600;
    private int height = 300;
    private Set<String> captureDevices;
    private Map<Long, ProjectPreferences> preferences = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectPreferences {
        private String taskDescription = "";
        private String taskTag = "";
        private boolean captureScreenshots = false;
        private String waitBeforeUpload;
    }
}

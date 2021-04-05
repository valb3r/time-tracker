package ua.timetracker.desktoptracker;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

@UtilityClass
public class ProjectFileStructUtil {

    @SneakyThrows
    public Path logDir() {
        File jarDir = jarFolder();
        return jarDir.toPath().resolve("timelogs");
    }

    @SneakyThrows
    public Path settingsFile() {
        File jarDir = jarFolder();
        return jarDir.toPath().resolve("tracker-settings.properties");
    }

    private File jarFolder() throws URISyntaxException {
        return new File(TimeTracker.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
    }
}

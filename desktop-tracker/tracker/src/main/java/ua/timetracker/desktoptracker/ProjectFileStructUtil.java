package ua.timetracker.desktoptracker;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Path;

@UtilityClass
public class ProjectFileStructUtil {

    @SneakyThrows
    public Path logDir() {
        File jarDir = new File(TimeTracker.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
        return jarDir.toPath().resolve("timelogs");
    }
}

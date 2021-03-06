package ua.timetracker.desktoptracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.*;
import ua.timetracker.desktoptracker.api.tracker.model.ProjectDto;
import ua.timetracker.desktoptracker.dto.TimeLogToUploadDto;
import ua.timetracker.desktoptracker.typeadapter.LocalDateTimeTypeAdapter;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class TimeTracker {

    private static final int MIN_REPORT_EACH_N_MS = 10_000;
    private static final int MAX_REPORT_EACH_N_MS = 600_000;
    private static final float JPEG_QUALITY = 0.3f;

    private final AtomicReference<TrackingData> trackingData = new AtomicReference<>();
    private final AtomicBoolean screenShotsEnabled = new AtomicBoolean();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).create();

    public TimeTracker() {
        startTimeTrackingThread();
    }

    public void startTracking(ProjectDto project, String taskDescription, String taskTag, Collection<GraphicsDevice> devices) {
        trackingData.set(new TrackingData(System.currentTimeMillis(), nextSchedule(project), taskDescription, taskTag, project, devices));
    }

    public void stopTracking() {
        trackingData.set(null);
    }

    public void setScreenshots(boolean enabled) {
        screenShotsEnabled.set(enabled);
    }

    private void startTimeTrackingThread() {
        Thread trackingThread = new Thread(() -> {
            while (true) {
                TrackingData data = trackingData.get();
                if (null == data || System.currentTimeMillis() < data.getReportAt()) { // Note that System.currentTimeMillis() is not necessary monotonic
                    LockSupport.parkNanos(MIN_REPORT_EACH_N_MS);
                    continue;
                }

                // Report
                trackingData.set(data.toBuilder().forTime(System.currentTimeMillis()).reportAt(nextSchedule(data.getProject())).build());
                captureTimeLog(data);
            }
        });
        trackingThread.start();
    }

    @SneakyThrows
    private void captureTimeLog(TrackingData data) {
        Path logDir = ProjectFileStructUtil.logDir();
        logDir.toFile().mkdir();
        String baseName = "" + Instant.now().toEpochMilli();
        val screenshotState = writeScreenshotIfNeeded(logDir, baseName, data.getProject(), data.getDevices());
        val duration = System.currentTimeMillis() - data.getForTime();
        if (duration <= 0) {
            return;
        }

        try (val writer = Files.newBufferedWriter(logDir.resolve(baseName), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW)) {
            val card = new TimeLogToUploadDto(
                    LocalDateTime.now(ZoneOffset.UTC),
                    duration,
                    data.getProject(),
                    data.getTaskDescription(),
                    data.getTaskTag(),
                    screenshotState.name()
            );
            gson.toJson(card, writer);
        }
    }

    @SneakyThrows
    private ScreenshotState writeScreenshotIfNeeded(Path dir, String baseName, ProjectDto project, Collection<GraphicsDevice> devices) {
        if (!screenShotsEnabled.get()) {
           return ScreenshotState.SCRD_DISABLED;
        }

        Rectangle screenRect = new Rectangle(0, 0, 0, 0);
        for (GraphicsDevice gd : devices) {
            screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
        }
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(screenRect));
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(null != project.getQuality() ? project.getQuality() : JPEG_QUALITY);

        try (ImageOutputStream os = ImageIO.createImageOutputStream(dir.resolve(baseName + ".jpg").toFile())) {
            jpgWriter.setOutput(os);
            IIOImage outputImage = new IIOImage(image, null, null);
            jpgWriter.write(null, outputImage, jpgWriteParam);
        } catch (RuntimeException ex) {
            return ScreenshotState.SCRD_SCR_FAILED;
        } finally {
            jpgWriter.dispose();
        }

        return ScreenshotState.SCRD_CAPTURED;
    }

    private long nextSchedule(ProjectDto project) {
        return System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(
                MIN_REPORT_EACH_N_MS,
                Math.max(MIN_REPORT_EACH_N_MS + 1, null != project.getIntervalminutes() ? (int) Duration.ofMinutes(project.getIntervalminutes()).toMillis() : MAX_REPORT_EACH_N_MS)
        );
    }

    @Getter
    @Builder(toBuilder = true)
    @RequiredArgsConstructor
    private static class TrackingData {
        private final long forTime;
        private final long reportAt;
        private final String taskDescription;
        private final String taskTag;
        private final ProjectDto project;
        private final Collection<GraphicsDevice> devices;
    }

    private enum ScreenshotState {
        SCRD_DISABLED,
        SCRD_SCR_FAILED,
        SCRD_CAPTURED
    }
}

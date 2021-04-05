package ua.timetracker.desktoptracker;

import com.google.gson.Gson;
import lombok.*;
import ua.timetracker.desktoptracker.api.tracker.model.ProjectDto;
import ua.timetracker.desktoptracker.dto.TimeLogToUploadDto;

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
    private final Gson gson = new Gson();

    public TimeTracker() {
        startTimeTrackingThread();
    }

    public void startTracking(ProjectDto project, String taskDescription, String taskTag) {
        trackingData.set(new TrackingData(System.currentTimeMillis(), nextSchedule(project), taskDescription, taskTag, project));
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
        writeScreenshotIfNeeded(logDir, baseName, data.getProject());
        val duration = System.currentTimeMillis() - data.getForTime();
        if (duration <= 0) {
            return;
        }

        Files.write(
                logDir.resolve(baseName),
                gson.toJson(new TimeLogToUploadDto(
                        duration,
                        data.getProject(),
                        data.getTaskDescription(),
                        data.getTaskTag()
                )).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE_NEW
        );
    }

    @SneakyThrows
    private void writeScreenshotIfNeeded(Path dir, String baseName, ProjectDto project) {
        if (!screenShotsEnabled.get()) {
           return;
        }

        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(null != project.getQuality() ? project.getQuality() : JPEG_QUALITY);

        try (ImageOutputStream os = ImageIO.createImageOutputStream(dir.resolve(baseName + ".jpg").toFile())) {
            jpgWriter.setOutput(os);
            IIOImage outputImage = new IIOImage(image, null, null);
            jpgWriter.write(null, outputImage, jpgWriteParam);
        } finally {
            jpgWriter.dispose();
        }
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
    }
}

package ua.timetracker.desktoptracker;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ua.timetracker.desktoptracker.api.tracker.model.ProjectDto;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class TimeTracker {

    private static final int REPORT_EACH_N_MS = 10_000;
    private static final float JPEG_QUALITY = 0.5f;

    private final AtomicReference<TrackingData> trackingData = new AtomicReference<>();
    private final AtomicBoolean screenShotsEnabled = new AtomicBoolean();
    private final Gson gson = new Gson();

    public TimeTracker() {
        startTimeTrackingThread();
    }

    public void startTracking(ProjectDto project) {
        trackingData.set(new TrackingData(nextSchedule(), project));
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
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(REPORT_EACH_N_MS));
                    continue;
                }

                // Report
                trackingData.set(data.toBuilder().reportAt(nextSchedule()).build());
                captureTimeLog(data);
            }
        });
        trackingThread.start();
    }

    @SneakyThrows
    private void captureTimeLog(TrackingData data) {
        File jarDir = new File(TimeTracker.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        Path logDir = jarDir.toPath().resolve("timelogs");
        logDir.toFile().mkdir();
        String baseName = "" + Instant.now().toEpochMilli();
        writeScreenshotIfNeeded(logDir, baseName);
        Files.write(logDir.resolve(baseName), gson.toJson(data).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
    }

    @SneakyThrows
    private void writeScreenshotIfNeeded(Path dir, String baseName) {
        if (!screenShotsEnabled.get()) {
           return;
        }

        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(JPEG_QUALITY);

        try (ImageOutputStream os = ImageIO.createImageOutputStream(dir.resolve(baseName + ".jpg").toFile())) {
            jpgWriter.setOutput(os);
            IIOImage outputImage = new IIOImage(image, null, null);
            jpgWriter.write(null, outputImage, jpgWriteParam);
        } finally {
            jpgWriter.dispose();
        }
    }

    private long nextSchedule() {
        return System.currentTimeMillis() + REPORT_EACH_N_MS;
    }

    @Getter
    @Builder(toBuilder = true)
    @RequiredArgsConstructor
    private static class TrackingData {
        private final long reportAt;
        private final ProjectDto project;
    }
}

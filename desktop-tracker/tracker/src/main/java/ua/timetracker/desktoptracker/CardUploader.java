package ua.timetracker.desktoptracker;

import com.google.gson.Gson;
import lombok.val;
import ua.timetracker.desktoptracker.api.tracker.TimeLogControllerApi;
import ua.timetracker.desktoptracker.api.tracker.model.TimeLogCreateOrUpdate;
import ua.timetracker.desktoptracker.dto.TimeLogToUploadDto;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class CardUploader {

    private static final int UPLOAD_EACH_N_MS = 10_000;

    private final Set<String> processedFiles = new HashSet<>();
    private final Gson gson = new Gson();
    private final AtomicReference<TimeLogControllerApi> api = new AtomicReference<>();
    private final AtomicLong nextUpload = new AtomicLong(System.currentTimeMillis());

    public CardUploader() {
        startCardUploadingThread();
    }

    public void setApi(TimeLogControllerApi api) {
        this.api.set(api);
    }

    private void startCardUploadingThread() {
        Thread uploadThread = new Thread(() -> {
            while (true) {
                long reportAt = nextUpload.get();
                if (System.currentTimeMillis() < reportAt) { // Note that System.currentTimeMillis() is not necessary monotonic
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(UPLOAD_EACH_N_MS));
                    continue;
                }

                // Report
                nextUpload.set(System.currentTimeMillis() + UPLOAD_EACH_N_MS);
                TimeLogControllerApi timeLog = api.get();
                if (null == timeLog) {
                    continue;
                }

                try {
                    doUploadCards();
                } catch (RuntimeException ex) {
                    // NOP
                }
            }
        });
        uploadThread.start();
    }

    private void doUploadCards() {
        File[] listOfFiles = ProjectFileStructUtil.logDir().toFile().listFiles(File::isFile);
        if (null == listOfFiles || 0 == listOfFiles.length) {
            return;
        }

        TimeLogControllerApi api = this.api.get();
        val time = OffsetDateTime.now(ZoneOffset.UTC).toLocalDate();
        val cards = api.uploadedTimeCards(time.atStartOfDay(), time.atTime(LocalTime.MAX));
        for (File report : listOfFiles) {
            if (report.getName().contains(".")) {
                continue;
            }

            if (processedFiles.contains(report.getAbsolutePath())) {
                report.delete();
                continue;
            }

            try {
                TimeLogToUploadDto toUpload;
                try (val reader = Files.newBufferedReader(report.toPath(), StandardCharsets.UTF_8)) {
                    toUpload = gson.fromJson(reader, TimeLogToUploadDto.class);
                } catch (Exception ex) {
                    continue;
                }

                // FIXME double submission problem
                val existingCard = cards.stream()
                        .filter(it -> Objects.equals(toUpload.getProject().getId(), it.getProjectid()))
                        .filter(it -> Objects.equals(toUpload.getTaskMessage(), it.getDescription()))
                        .findAny();

                if (existingCard.isPresent()) {
                    val card = existingCard.get();
                    api.updateTimeLog(
                            card.getId(),
                            new TimeLogCreateOrUpdate()
                                    .description(card.getDescription())
                                    .timestamp(card.getTimestamp())
                                    .location(card.getLocation())
                                    .tags(card.getTags())
                                    .duration(getSeconds(toUpload).plus(null == card.getDuration() ? Duration.ofMillis(0L) : Duration.parse(card.getDuration())).toString())
                                    .projectid(card.getProjectid())
                    );
                    // If anything prevents file removal - TODO double - submission
                    processedFiles.add(report.getAbsolutePath());
                    report.delete();
                    uploadImageIfPossible(api, toUpload, report, card);
                    continue;
                }

                val card = api.uploadTimelog(
                        new TimeLogCreateOrUpdate()
                                .projectid(toUpload.getProject().getId())
                                .description(toUpload.getTaskMessage())
                                .tags(Collections.singletonList(toUpload.getTaskTag()))
                                .duration(getSeconds(toUpload).toString())
                                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                                .location("UNKNOWN")
                );
                uploadImageIfPossible(api, toUpload, report, card);

            } catch (Exception ex) {
                // NOP
            }
        }
    }

    private Duration getSeconds(TimeLogToUploadDto toUpload) {
        return Duration.ofMillis(toUpload.getLoggedDuration());
    }

    private void uploadImageIfPossible(TimeLogControllerApi api, TimeLogToUploadDto toUpload, File report, ua.timetracker.desktoptracker.api.tracker.model.TimeLogDto card) {
        val imageFile = Paths.get(report.getAbsolutePath() + ".jpg").toFile();
        if (Paths.get(report.getAbsolutePath() + ".jpg").toFile().exists()) {
            api.uploadTimelogImage(
                    card.getId(),
                    "screenshot",
                    imageFile,
                    Duration.ofMillis(toUpload.getLoggedDuration()).toString(),
                    null == toUpload.getForTime() ? card.getTimestamp() : toUpload.getForTime()
            );
            imageFile.delete();
        }
    }
}

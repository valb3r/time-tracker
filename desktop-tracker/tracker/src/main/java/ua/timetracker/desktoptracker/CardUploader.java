package ua.timetracker.desktoptracker;

import com.google.gson.Gson;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ua.timetracker.desktoptracker.api.tracker.TimeLogControllerApi;
import ua.timetracker.desktoptracker.api.tracker.invoker.ApiException;
import ua.timetracker.desktoptracker.api.tracker.model.TimeLogCreateOrUpdate;
import ua.timetracker.desktoptracker.api.tracker.model.TimeLogDto;
import ua.timetracker.desktoptracker.dto.TimeLogToUploadDto;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class CardUploader {

    private static final int UPLOAD_EACH_N_MS = 10_000;

    private final Set<String> processedFiles = new HashSet<>();
    private final Gson gson = new Gson();
    private final AtomicReference<TimeLogControllerApi> api = new AtomicReference<>();
    private final AtomicLong nextUpload = new AtomicLong(System.currentTimeMillis());

    @Setter
    private Supplier<TimeLogControllerApi> reLogin;

    @Setter
    private Consumer<Long> updateTimeLogged;

    public CardUploader() {
        startCardUploadingThread();
    }

    public void setApi(TimeLogControllerApi api) {
        this.api.set(api);
    }

    private void startCardUploadingThread() {
        Thread uploadThread = new Thread(() -> {
            boolean isInit = false;
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

                if (null != updateTimeLogged) {
                    if (!isInit) {
                        try {
                            val time = OffsetDateTime.now(ZoneOffset.UTC).toLocalDate();
                            val cards = new ArrayList<>(timeLog.uploadedTimeCards(time.atStartOfDay(), time.atTime(LocalTime.MAX)));
                            updateTimeLogged.accept(cards.stream().mapToLong(it -> Duration.parse(it.getDuration()).toMillis()).sum());
                            isInit = true;
                        } catch (Exception ex) {
                            log.warn("Failed prefetch of worked time {}", ex.getMessage());
                        }
                    }
                    updateTimeLogged.accept(null);
                }

                try {
                    doUploadCards();
                } catch (RuntimeException ex) {
                    log.warn("Upload failed {}", ex.getMessage());
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
        val cards = new ArrayList<TimeLogDto>();
        try {
            cards.addAll(api.uploadedTimeCards(time.atStartOfDay(), time.atTime(LocalTime.MAX)));
        } catch (ApiException ex) {
            doRelogin();
        } catch (Exception ex) {
            log.warn("Failed to list cards {}", ex.getMessage());
            return;
        }

        updateTimeLogged.accept(cards.stream().mapToLong(it -> Duration.parse(it.getDuration()).toMillis()).sum());
        for (File report : listOfFiles) {
            if (report.getName().contains(".")) {
                continue;
            }

            if (processedFiles.contains(report.getAbsolutePath())) {
                log.warn("Already processed {}", report.getAbsolutePath());
                report.delete();
                continue;
            }

            try {
                TimeLogToUploadDto toUpload;
                try (val reader = Files.newBufferedReader(report.toPath(), StandardCharsets.UTF_8)) {
                    toUpload = gson.fromJson(reader, TimeLogToUploadDto.class);
                } catch (Exception ex) {
                    log.warn("Failed parsing {}", report.getAbsolutePath());
                    continue;
                }

                // FIXME double submission problem
                val existingCard = cards.stream()
                        .filter(it -> Objects.equals(toUpload.getProject().getId(), it.getProjectid()))
                        .filter(it -> Objects.equals(toUpload.getTaskMessage(), it.getDescription()))
                        .findAny();

                if (existingCard.isPresent()) {
                    val card = existingCard.get();
                    api.incrementTimeLog(
                            card.getId(),
                            uploadDuration(toUpload).toString()
                    );
                    // If anything prevents file removal - TODO double - submission
                    uploadImagesAndCleanup(api, report, toUpload, card);
                    continue;
                }

                val card = api.uploadTimelog(
                        new TimeLogCreateOrUpdate()
                                .projectid(toUpload.getProject().getId())
                                .description(toUpload.getTaskMessage())
                                .tags(Collections.singletonList(toUpload.getTaskTag()))
                                .duration(uploadDuration(toUpload).toString())
                                .timestamp(null != toUpload.getForTime() ? toUpload.getForTime() : LocalDateTime.now(ZoneOffset.UTC))
                                .location("UNKNOWN")
                );
                cards.add(card);
                uploadImagesAndCleanup(api, report, toUpload, card);

            } catch (ApiException ex) {
                doRelogin();
            } catch (Exception ex) {
                log.warn("Failed to upload {} of {}", ex.getMessage(), report.getAbsolutePath());
            }
        }
    }

    private void doRelogin() {
        try {
            this.api.set(reLogin.get());
        } catch (Exception reloginEx) {
            // NOP
        }
    }

    private void uploadImagesAndCleanup(TimeLogControllerApi api, File report, TimeLogToUploadDto toUpload, ua.timetracker.desktoptracker.api.tracker.model.TimeLogDto card) {
        processedFiles.add(report.getAbsolutePath());
        report.delete();
        uploadImageIfPossible(api, toUpload, report, card);
    }

    private Duration uploadDuration(TimeLogToUploadDto toUpload) {
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

package ua.timetracker.desktoptracker;

import ua.timetracker.desktoptracker.api.tracker.TimeLogControllerApi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class CardUploader {

    private static final int UPLOAD_EACH_N_MS = 10_000;

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

                System.out.println("Uploading...");
            }
        });
        uploadThread.start();
    }
}

package ua.timetracker.desktoptracker;

import com.formdev.flatlaf.FlatLightLaf;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import ua.timetracker.desktoptracker.api.admin.LoginControllerApi;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiClient;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiException;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiResponse;
import ua.timetracker.desktoptracker.api.admin.invoker.JSON;
import ua.timetracker.desktoptracker.api.admin.model.LoginDto;
import ua.timetracker.desktoptracker.api.tracker.TimeLogControllerApi;
import ua.timetracker.desktoptracker.api.tracker.model.ProjectDto;
import ua.timetracker.desktoptracker.dto.PreferencesDto;
import ua.timetracker.desktoptracker.verifier.MinLenVerifier;
import ua.timetracker.desktoptracker.verifier.NotBlankVerifier;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class TrackerDialog {
    public static final String DEFAULT_TITLE = "Time Tracker";

    private final Set<Long> projectsLoaded = new HashSet<>();

    private final JFrame frame;
    private final TimeTracker tracker;
    private final CardUploader uploader;
    private final PreferencesDto preferences;

    private JTabbedPane trackerTabs;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JTextField apiUploadUrl;
    private JPanel loginPanel;
    private JPanel timeTrackingPanel;
    private JPanel mainPanel;
    private JComboBox currentProject;
    private JButton start;
    private JButton stop;
    private JCheckBox captureScreenshots;
    private JTextArea taskDescription;
    private JComboBox workType;
    private JPanel logoutPanel;
    private JLabel loginErrorMessage;
    private JLabel trackingErrorMessage;

    private TimeLogControllerApi timeLog;
    private String authCookie;
    private ProjectComponent activeProject;

    public TrackerDialog(JFrame frame, TimeTracker tracker, CardUploader uploader, PreferencesDto preferences) {
        this.frame = frame;
        this.tracker = tracker;
        this.uploader = uploader;
        this.preferences = preferences;
        this.usernameField.setText(this.preferences.getUsername());
        this.apiUploadUrl.setText(this.preferences.getApiUrl());
        setupUiAndHandlers();
    }

    private void setupUiAndHandlers() {
        usernameField.setInputVerifier(new NotBlankVerifier());
        passwordField.setInputVerifier(new NotBlankVerifier());
        apiUploadUrl.setInputVerifier(new MinLenVerifier(5));
        taskDescription.setInputVerifier(new MinLenVerifier(5));

        loginButton.addActionListener(e -> doLogin());

        trackerTabs.addChangeListener(e -> {
            if (trackerTabs.getSelectedIndex() == 2) {
                doLogout();
            }
            if (trackerTabs.getSelectedIndex() == 1) {
                doLoadTrackingScreenData();
            }
        });

        start.addActionListener(e -> {
            if (null == activeProject) {
                return;
            }

            if (!taskDescription.getInputVerifier().verify(taskDescription)) {
                trackingErrorMessage.setText("Task description is too short");
                return;
            }
            trackingErrorMessage.setText("");

            this.frame.setTitle(String.format("[%s] Tracking...", activeProject.getProject().getCode()));
            tracker.setScreenshots(captureScreenshots.isSelected());
            tracker.startTracking(activeProject.getProject(), taskDescription.getText(), (String) workType.getSelectedItem());
            currentProject.setEnabled(false);
            taskDescription.setEnabled(false);
            workType.setEnabled(false);
            start.setEnabled(false);
            stop.setEnabled(true);
        });

        stop.addActionListener(e -> {
            this.frame.setTitle(DEFAULT_TITLE);
            tracker.stopTracking();
            currentProject.setEnabled(true);
            taskDescription.setEnabled(true);
            workType.setEnabled(true);
            start.setEnabled(true);
            stop.setEnabled(false);
        });

        currentProject.addActionListener(e -> {
            activeProject = (ProjectComponent) currentProject.getSelectedItem();
            if (null == activeProject) {
                return;
            }

            workType.removeAllItems();
            captureScreenshots.setSelected(Boolean.TRUE.equals(activeProject.getProject().getScreenshots()));
            activeProject.getProject().getActivities().forEach(it -> workType.addItem(it));

            propagateProjectPrefs();
        });

        captureScreenshots.addActionListener(e -> tracker.setScreenshots(captureScreenshots.isSelected()));
    }

    private void propagateProjectPrefs() {
        Long projectId = activeProject.getProject().getId();
        if (projectsLoaded.contains(projectId)) {
            return;
        }
        PreferencesDto.ProjectPreferences preferences = this.preferences.getPreferences().get(projectId);
        projectsLoaded.add(projectId);
        if (null != preferences) {
            taskDescription.setText(preferences.getTaskDescription());
            captureScreenshots.setSelected(preferences.isCaptureScreenshots());
            IntStream.range(0, workType.getItemCount()).forEach(it -> {
                if (preferences.getTaskTag().equals(workType.getItemAt(it))) {
                    workType.setSelectedIndex(it);
                }
            });
        }
    }

    private void doLogin() {
        if (!usernameField.getInputVerifier().verify(usernameField)) {
            loginErrorMessage.setText("Login is empty");
            return;
        }

        if (!passwordField.getInputVerifier().verify(passwordField)) {
            loginErrorMessage.setText("Password is empty");
            return;
        }

        if (!apiUploadUrl.getInputVerifier().verify(apiUploadUrl)) {
            loginErrorMessage.setText("API URL is too short");
            return;
        }
        loginErrorMessage.setText("");

        this.loginButton.setEnabled(false);
        new Thread(() -> {
            try {
                ApiResponse<?> loginResp = apiLogin();
                SwingUtilities.invokeLater(() -> {
                    buildTrackerApi(loginResp);
                    this.uploader.setApi(timeLog);
                    trackerTabs.setEnabledAt(2, true);
                    trackerTabs.setEnabledAt(1, true);
                    trackerTabs.setEnabledAt(0, false);
                    trackerTabs.setSelectedIndex(1);
                    loginErrorMessage.setText("");
                });
            } catch (ApiException ex) {
                SwingUtilities.invokeLater(() -> loginErrorMessage.setText(errorMessage(ex)));
            } finally {
                this.loginButton.setEnabled(true);
            }
        }).start();
    }

    private ApiResponse<?> apiLogin() {
        LoginControllerApi loginApi = new LoginControllerApi(new ApiClient().setBasePath(apiUploadUrl.getText() + "/admin-api"));
        return loginApi.loginWithHttpInfo(new LoginDto().username(usernameField.getText()).password(new String(passwordField.getPassword())));
    }

    private TimeLogControllerApi buildTrackerApi(ApiResponse<?> loginResp) {
        authCookie = loginResp.getHeaders().get("set-cookie").get(0).split("=")[1];
        timeLog = new TimeLogControllerApi(
                new ua.timetracker.desktoptracker.api.tracker.invoker.ApiClient()
                        .setBasePath(apiUploadUrl.getText() + "/tracker-api")
                        .addDefaultCookie("X-Authorization", authCookie)
                        // FIXME https://github.com/swagger-api/swagger-codegen/issues/6992
                        .setJSON(new ua.timetracker.desktoptracker.api.tracker.invoker.JSON().setGson(JSON.createGson().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).create()))
        );

        return timeLog;
    }

    private void doLoadTrackingScreenData() {
        this.start.setEnabled(false);
        this.stop.setEnabled(false);
        this.trackingErrorMessage.setText("");
        new Thread(() -> {
            try {
                List<ProjectDto> projects = timeLog.availableProjects();
                SwingUtilities.invokeLater(() -> {
                    currentProject.removeAllItems();
                    projects.forEach(it -> currentProject.addItem(new ProjectComponent(it)));
                    this.start.setEnabled(true);
                    trackingErrorMessage.setText("");
                });
            } catch (ua.timetracker.desktoptracker.api.tracker.invoker.ApiException ex) {
                SwingUtilities.invokeLater(() -> trackingErrorMessage.setText(errorMessage(ex)));
            } finally {
                this.loginButton.setEnabled(true);
            }
        }).start();
    }

    private void doLogout() {
        tracker.stopTracking();
        authCookie = null;
        trackerTabs.setEnabledAt(0, true);
        trackerTabs.setEnabledAt(1, false);
        trackerTabs.setEnabledAt(2, false);
        trackerTabs.setSelectedIndex(0);
    }

    private static String errorMessage(Throwable ex) {
        return String.format("Error: %s", ex.getMessage());
    }

    @Getter
    public static class ProjectComponent {
        private final ProjectDto project;

        public ProjectComponent(ProjectDto project) {
            this.project = project;
        }

        @Override
        public String toString() {
            return String.format("[%s] %s", project.getCode(), project.getName());
        }
    }

    class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter out, LocalDateTime date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    return LocalDateTime.parse(date, formatter);
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        FlatLightLaf.install();
        JFrame frame = new JFrame(DEFAULT_TITLE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("icons/clock.png")));
        val preferences = loadPreferences();
        val uploader = new CardUploader();
        TrackerDialog dialog = new TrackerDialog(frame, new TimeTracker(), uploader, preferences);
        uploader.setReLogin(() -> {
            val login = dialog.apiLogin();
            return dialog.buildTrackerApi(login);
        });

        frame.setContentPane(dialog.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(preferences.getWidth(), preferences.getHeight());
        dialog.trackerTabs.setEnabledAt(1, false);
        dialog.trackerTabs.setEnabledAt(2, false);
        frame.setVisible(true);
        setupShutdownHook(dialog);
    }

    @SneakyThrows
    private static PreferencesDto loadPreferences() {
        File settingsFile = ProjectFileStructUtil.settingsFile().toFile();
        if (!settingsFile.exists()) {
            return new PreferencesDto();
        }

        PreferencesDto preferences = null;
        try (val reader =  Files.newBufferedReader(settingsFile.toPath(), StandardCharsets.UTF_8)) {
            preferences = new Gson().fromJson(reader, PreferencesDto.class);
        } catch (Exception ex) {
            // NOP
        }

        return null == preferences ? new PreferencesDto() : preferences;
    }

    private static void setupShutdownHook(TrackerDialog dialog) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            val preferences = new PreferencesDto(
                    dialog.usernameField.getText(),
                    dialog.apiUploadUrl.getText(),
                    dialog.frame.getWidth(),
                    dialog.frame.getHeight(),
                    null == dialog.activeProject ? null :
                            ImmutableMap.of(
                                    dialog.activeProject.getProject().getId(),
                                    new PreferencesDto.ProjectPreferences(
                                            dialog.taskDescription.getText(),
                                            (String) dialog.workType.getSelectedItem(),
                                            dialog.captureScreenshots.isSelected()
                                    )
                            )
            );

            try (val writer =  Files.newBufferedWriter(ProjectFileStructUtil.settingsFile(), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
                new Gson().toJson(preferences, writer);
            } catch (Exception ex) {
                // NOP
            }
        }));
    }
}

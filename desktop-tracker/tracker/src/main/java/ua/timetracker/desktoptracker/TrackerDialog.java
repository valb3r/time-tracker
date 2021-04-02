package ua.timetracker.desktoptracker;

import com.formdev.flatlaf.FlatLightLaf;
import lombok.Getter;
import lombok.SneakyThrows;
import ua.timetracker.desktoptracker.api.admin.LoginControllerApi;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiClient;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiException;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiResponse;
import ua.timetracker.desktoptracker.api.admin.model.LoginDto;
import ua.timetracker.desktoptracker.api.tracker.TimeLogControllerApi;
import ua.timetracker.desktoptracker.api.tracker.model.ProjectDto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TrackerDialog {
    public static final String DEFAULT_TITLE = "Time Tracker";

    private final JFrame frame;
    private final TimeTracker tracker;
    private final CardUploader uploader;

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

    public TrackerDialog(JFrame frame, TimeTracker tracker, CardUploader uploader) {
        this.frame = frame;
        this.tracker = tracker;
        this.uploader = uploader;
        setupUiHandlers();
    }

    private void setupUiHandlers() {
        loginButton.addActionListener(e -> doLogin().start());

        trackerTabs.addChangeListener(e -> {
            if (trackerTabs.getSelectedIndex() == 2) {
                doLogout();
            }
            if (trackerTabs.getSelectedIndex() == 1) {
                doLoadTrackingScreenData().start();
            }
        });

        start.addActionListener(e -> {
            if (null == activeProject) {
                return;
            }

            this.frame.setTitle(String.format("[%s] Tracking...", activeProject.getProject().getCode()));
            tracker.setScreenshots(captureScreenshots.isSelected());
            tracker.startTracking(activeProject.getProject());
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
            activeProject.getProject().getActivities().forEach(it -> workType.addItem(it));
        });

        captureScreenshots.addActionListener(e -> tracker.setScreenshots(captureScreenshots.isSelected()));
    }

    private Thread doLogin() {
        LoginControllerApi loginApi = new LoginControllerApi(new ApiClient().setBasePath(apiUploadUrl.getText() + "/admin-api"));
        this.loginButton.setEnabled(false);
        return new Thread(() -> {
            try {
                ApiResponse<?> resp = loginApi.loginWithHttpInfo(new LoginDto().username(usernameField.getText()).password(new String(passwordField.getPassword())));
                SwingUtilities.invokeLater(() -> {
                    authCookie = resp.getHeaders().get("set-cookie").get(0).split("=")[1];
                    timeLog = new TimeLogControllerApi(
                            new ua.timetracker.desktoptracker.api.tracker.invoker.ApiClient().setBasePath(apiUploadUrl.getText() + "/tracker-api").addDefaultCookie("X-Authorization", authCookie)
                    );
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
        });
    }

    private Thread doLoadTrackingScreenData() {
        this.start.setEnabled(false);
        this.stop.setEnabled(false);
        return new Thread(() -> {
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
        });
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

    @SneakyThrows
    public static void main(String[] args) {
        FlatLightLaf.install();
        JFrame frame = new JFrame(DEFAULT_TITLE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("icons/clock.png")));
        TrackerDialog dialog = new TrackerDialog(frame, new TimeTracker(), new CardUploader());
        frame.setContentPane(dialog.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 200);
        dialog.trackerTabs.setEnabledAt(1, false);
        dialog.trackerTabs.setEnabledAt(2, false);
        frame.setVisible(true);
    }
}

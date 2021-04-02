package ua.timetracker.desktoptracker;

import com.formdev.flatlaf.FlatLightLaf;
import lombok.SneakyThrows;

import javax.swing.*;

public class TrackerDialog {
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
    private JButton logout;

    public TrackerDialog() {
        loginButton.addActionListener(e -> {
            trackerTabs.setEnabledAt(1, true);
            trackerTabs.setEnabledAt(0, false);
            trackerTabs.setSelectedIndex(1);
        });

        logout.addActionListener(e -> {
            trackerTabs.setEnabledAt(0, true);
            trackerTabs.setEnabledAt(1, false);
            trackerTabs.setSelectedIndex(0);
        });
    }

    @SneakyThrows
    public static void main(String[] args) {
        FlatLightLaf.install();
        JFrame frame = new JFrame("Time Tracker");
        TrackerDialog dialog = new TrackerDialog();
        frame.setContentPane(dialog.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 200);
        dialog.trackerTabs.setEnabledAt(1, false);
        frame.setVisible(true);
    }
}

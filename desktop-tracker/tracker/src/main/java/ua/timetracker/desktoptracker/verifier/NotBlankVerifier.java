package ua.timetracker.desktoptracker.verifier;

import javax.swing.*;

public class NotBlankVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        return !((JTextField) input).getText().trim().isEmpty();
    }
}

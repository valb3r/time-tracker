package ua.timetracker.desktoptracker.verifier;

import lombok.RequiredArgsConstructor;

import javax.swing.*;

@RequiredArgsConstructor
public class MinLenVerifier extends InputVerifier {

    private final int minLen;

    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextField) {
            return ((JTextField) input).getText().trim().length() >= minLen;
        }

        if (input instanceof JTextArea) {
            return ((JTextArea) input).getText().trim().length() >= minLen;
        }

        throw new IllegalStateException("Wrong input type");
    }
}

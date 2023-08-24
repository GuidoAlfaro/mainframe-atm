package bo.edu.ucb.sis213;

import javax.swing.*;

import bo.edu.ucb.sis213.views.Welcome;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Welcome welcomeFrame = new Welcome();
            welcomeFrame.setVisible(true);
        });
    }
}

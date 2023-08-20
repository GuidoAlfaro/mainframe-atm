package bo.edu.ucb.sis213.frames;
import javax.swing.*;

import bo.edu.ucb.sis213.PasswordHandler;
import bo.edu.ucb.sis213.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PIN extends JFrame {
    JPasswordField currentPINField;
    JPasswordField newPINField;
    JPasswordField confirmPINField;
    User user;
    public PIN(User user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Cambiar PIN");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300)); // Aumenta el ancho y la altura del JFrame
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        createTitlePanel(mainPanel);
        createFormPanel(mainPanel);
        createButtonPanel(mainPanel);

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void createTitlePanel(JPanel mainPanel) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 144, 255));
        titlePanel.setPreferredSize(new Dimension(getWidth(), 60));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Cambiar PIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }

    private void createFormPanel(JPanel mainPanel) {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);

        JLabel currentPINLabel = new JLabel("Antigua contraseña:");
        currentPINLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(currentPINLabel, gbc);

        gbc.gridy++;
        JLabel newPINLabel = new JLabel("Nueva contraseña:");
        newPINLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(newPINLabel, gbc);

        gbc.gridy++;
        JLabel confirmPINLabel = new JLabel("Confirmar contraseña:");
        confirmPINLabel.setFont(new Font("Arial", Font.BOLD, 20));
        formPanel.add(confirmPINLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        currentPINField = new JPasswordField(10); // Aumenta el tamaño del campo
        formPanel.add(currentPINField, gbc);

        gbc.gridy++;
        newPINField = new JPasswordField(10); // Aumenta el tamaño del campo
        formPanel.add(newPINField, gbc);

        gbc.gridy++;
        confirmPINField = new JPasswordField(10); // Aumenta el tamaño del campo
        formPanel.add(confirmPINField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel(JPanel mainPanel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(200, 200, 200));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(cancelButton);

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setFont(new Font("Arial", Font.BOLD, 20));
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentPIN = PasswordHandler.passwordToInt(currentPINField);
                int newPIN = PasswordHandler.passwordToInt(newPINField);
                int confirmPIN = PasswordHandler.passwordToInt(confirmPINField);
                user.cambiarPIN(currentPIN, newPIN, confirmPIN);
                dispose();
            }
        });
        buttonPanel.add(acceptButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(PIN::new);
    // }
}

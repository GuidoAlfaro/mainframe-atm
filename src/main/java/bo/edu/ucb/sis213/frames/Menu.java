package bo.edu.ucb.sis213.frames;
import javax.swing.*;

import bo.edu.ucb.sis213.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    User user;
    public Menu(User user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Cajero Automático");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        

        JPanel mainPanel = new JPanel(new BorderLayout());

        createTopBar(mainPanel);
        createButtons(mainPanel);

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void createTopBar(JPanel mainPanel) {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(135, 206, 235)); // Fondo celeste

        JLabel welcomeLabel = new JLabel("¡Bienvenido!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE); // Letras blancas
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        topBar.add(welcomeLabel);

        mainPanel.add(topBar, BorderLayout.NORTH);
    }

    private void createButtons(JPanel mainPanel) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);

        String[] buttonLabels = {
            "Consultar saldo",
            "Realizar un depósito",
            "Realizar un retiro",
            "Transacciones",
            "Cambiar PIN",
            "Salir"
        };
        String[] images = {
            "images/total.png",
            "images/deposit.png",
            "images/withdraw.png",
            "images/transactions.png",
            "images/lock.png",
            "images/logout.png"
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        for (int i = 0; i < buttonLabels.length; i++) {
            buttonPanel.add(createButtonPanel(buttonLabels[i], images[i]), gbc);
            gbc.gridy++;
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private JPanel createButtonPanel(String text, String iconPath) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(iconPath); // Cambia la ruta de la imagen
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        JButton button = new JButton(text, icon);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(200, 200, 200));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(300, 40));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opción seleccionada: " + text);
                //DepositFrame depositFrame = new DepositFrame();
                //depositFrame.setVisible(true);
                //switch case para llamar a las otras ventanas
                switch (text) {
                    case "Consultar saldo":
                        new Saldo(user);
                        break;
                    case "Realizar un depósito":
                        new Deposito(user);
                        break;
                    case "Realizar un retiro":
                        new Retiro(user);
                        break;
                    case "Transacciones":
                        new ExtractoBancario(user);
                        break;
                    case "Cambiar PIN":
                        new PIN(user);
                        break;
                    case "Salir":
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                
            }
        });

        buttonPanel.add(button, BorderLayout.CENTER);
        return buttonPanel;
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(Menu::new);
    // }
}
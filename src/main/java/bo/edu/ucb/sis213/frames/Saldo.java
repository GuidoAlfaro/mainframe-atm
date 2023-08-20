package bo.edu.ucb.sis213.frames;
import javax.swing.*;

import bo.edu.ucb.sis213.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Saldo extends JFrame {

    User user;
    public Saldo(User user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Saldo Total");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(700, 350));
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        createBankPanel(mainPanel);
        createReceiptPanel(mainPanel);
        createButtonPanel(mainPanel);

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void createBankPanel(JPanel mainPanel) {
        JPanel bankPanel = new JPanel();
        bankPanel.setBackground(new Color(30, 144, 255));
        bankPanel.setPreferredSize(new Dimension(getWidth(), 60));
        bankPanel.setLayout(new BorderLayout());

        ImageIcon bankIcon = new ImageIcon("images/bank_icon.png");
        Image scaledBankIcon = bankIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        bankIcon = new ImageIcon(scaledBankIcon);

        JLabel iconLabel = new JLabel(bankIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        bankPanel.add(iconLabel, BorderLayout.WEST);

        JLabel bankLabel = new JLabel("Banco de Ejemplo");
        bankLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Aumenta el tamaño de la letra
        bankLabel.setForeground(Color.WHITE);
        bankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bankPanel.add(bankLabel, BorderLayout.CENTER);
        
        mainPanel.add(bankPanel, BorderLayout.NORTH);
    }

    private void createReceiptPanel(JPanel mainPanel) {
        JPanel receiptPanel = new JPanel();
        receiptPanel.setBackground(Color.WHITE);
        receiptPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel messageLabel = new JLabel("¡Gracias por confiar en nuestro banco!");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Aumenta el tamaño de la letra
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        receiptPanel.add(messageLabel);

        JLabel balanceLabel = new JLabel("Saldo Total del Cliente:");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Aumenta el tamaño de la letra
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        receiptPanel.add(balanceLabel);

        JLabel amountLabel = new JLabel("Bs."+user.getSaldo());
        amountLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Aumenta el tamaño de la letra
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        receiptPanel.add(amountLabel);

        mainPanel.add(receiptPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel(JPanel mainPanel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(200, 200, 200));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0)); // Reduce el espacio inferior

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setFont(new Font("Arial", Font.BOLD, 20)); // Aumenta el tamaño de la letra
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(acceptButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(Saldo::new);
    // }
}

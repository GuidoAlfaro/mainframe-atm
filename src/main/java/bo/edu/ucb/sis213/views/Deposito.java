package bo.edu.ucb.sis213.views;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import bo.edu.ucb.sis213.bl.UserBl;
import bo.edu.ucb.sis213.dto.UsuarioDto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Deposito extends JFrame {

    private JTextField amountField;
    private UsuarioDto user;
    public Deposito(UsuarioDto user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Depósito");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 300));
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 0, 180));

        createAmountField(mainPanel);
        createButtons(mainPanel);

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void createAmountField(JPanel mainPanel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 80));
        panel.setOpaque(false);

        JLabel prefixLabel = new JLabel("Bs");
        prefixLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        prefixLabel.setForeground(Color.WHITE);
        panel.add(prefixLabel);

        amountField = new JTextField(10);
        amountField.setFont(new Font("Arial", Font.PLAIN, 36));
        amountField.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el contenido
        amountField.setDocument(new NumericDocument());
        panel.add(amountField);

        mainPanel.add(panel, BorderLayout.CENTER);
    }

    private void createButtons(JPanel mainPanel) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setOpaque(false);

        JButton depositButton = new JButton("Depositar");
        depositButton.setFont(new Font("Arial", Font.BOLD, 30));
        depositButton.setForeground(Color.WHITE);
        depositButton.setBackground(new Color(30, 144, 255));
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = amountField.getText();
                try {
                    UserBl userBl = new UserBl();
                    userBl.realizarDeposito(Double.parseDouble(amount), user.getAlias());
                    userBl.historicoRegister(Double.parseDouble(amount), "Deposito", user.getAlias());
                    JOptionPane.showMessageDialog(null, "Depósito realizado con éxito");
                    dispose();
                } catch (RuntimeException exc) {
                    JOptionPane.showMessageDialog(null, "Ingrese un monto válido.");
                } catch (SQLException exc) {
                    JOptionPane.showMessageDialog(null, "Error al realizar el depósito");
                    dispose();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Error al realizar el depósito");
                    dispose();
                }
            }
        });
        buttonPanel.add(depositButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 30));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(255, 69, 0));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private class NumericDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str != null) {
                try {
                    int input = Integer.parseInt(str);
                    if (input >= 0) {
                        super.insertString(offs, str, a);
                    }
                } catch (NumberFormatException e) {
                    // Ignorar si no es un número válido
                }
            }
        }
    }  

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(DepositFrame::new);
    // }
}

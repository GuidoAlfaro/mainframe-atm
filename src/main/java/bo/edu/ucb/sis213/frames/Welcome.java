package bo.edu.ucb.sis213.frames;

import javax.swing.*;
import javax.swing.text.PlainDocument;

import bo.edu.ucb.sis213.PasswordHandler;
import bo.edu.ucb.sis213.User;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Welcome extends JFrame {

    JPasswordField pinField = new JPasswordField(4);
    JTextField aliasField = new JTextField(15);

    public Welcome() {
        // Configurar el JFrame
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Ajusta el tamaño del JFrame
        setResizable(false);
        setLocationRelativeTo(null);

        // Barra de título con imagen y nombre del banco
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(30, 144, 255));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon logoIcon = new ImageIcon("images/bank_icon.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        titlePanel.add(logoLabel, BorderLayout.WEST);

        JLabel bankNameLabel = new JLabel("Trapnest ATM");
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bankNameLabel.setForeground(Color.WHITE);
        bankNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(bankNameLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);

        // Panel principal con fondo
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(240, 240, 240));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridLayout(6, 2, 0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Etiquetas y campos de texto
        JLabel aliasLabel = new JLabel("Alias:");
        aliasLabel.setFont(new Font("Arial", Font.BOLD, 24));
        aliasLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(aliasLabel);

        aliasField.setFont(new Font("Arial", Font.PLAIN, 40));
        aliasField.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(aliasField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 24));
        pinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(pinLabel);

        pinField.setFont(new Font("Arial", Font.PLAIN, 40));
        pinField.setHorizontalAlignment(SwingConstants.CENTER);
        pinField.setDocument(new JTextFieldLimit(4)); // Limitar a 4 caracteres
        pinField.addKeyListener(new KeyTypedAdapter());
        mainPanel.add(pinField);

        mainPanel.add(new JLabel()); // Espacio vacío

        // Botones Iniciar Sesión y Salir en un nuevo panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Espaciado horizontal
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            // Acciones al hacer clic en el botón de inicio de sesión
        });
        buttonPanel.add(loginButton);

        JButton exitButton = new JButton("Cancelar");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setBackground(new Color(30, 144, 255));
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);

        // Agregar el panel de botones al centro del panel principal
        mainPanel.add(buttonPanel);

        // Agregar el panel principal al JFrame
        add(mainPanel);

        // Mostrar el JFrame
        setVisible(true);

        //button listener
        loginButton.addActionListener(e -> {
            String alias = aliasField.getText();
            String pin = String.valueOf(pinField.getPassword());
            if (alias.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un alias y un PIN.");
            } else {
                int PIN = PasswordHandler.passwordToInt(pinField);
                User user = new User(PIN, aliasField.getText());
                boolean result = user.login();
                if(result){
                    //new Saldo(user);
                    new Menu(user);
                }
            }
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    // Clase para limitar el número de caracteres en el campo de contraseña
    private static class JTextFieldLimit extends PlainDocument {
        private final int limit;

        JTextFieldLimit(int limit) {
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) {
            if (str == null) return;

            if ((getLength() + str.length()) <= limit) {
                try {
                    super.insertString(offset, str, attr);
                } catch (javax.swing.text.BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Clase para permitir solo números en el campo de contraseña
    private static class KeyTypedAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c)) {
                e.consume();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Welcome::new);
    }
}

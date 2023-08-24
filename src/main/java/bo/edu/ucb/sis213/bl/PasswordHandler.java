package bo.edu.ucb.sis213.bl;

import javax.swing.JPasswordField;

public class PasswordHandler {
    public static int passwordToInt(JPasswordField passwordField) {
        // Obtén el contenido del campo de contraseña como una cadena
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // Convierte la cadena a un número entero
        int valorNumerico;
        try {
            valorNumerico = Integer.parseInt(password);
            return valorNumerico;
        } catch (NumberFormatException ex) {
            System.out.println("La contraseña no es un número válido.");
        }
        return -1;
    }
}

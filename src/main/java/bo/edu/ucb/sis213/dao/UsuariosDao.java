package bo.edu.ucb.sis213.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;

import bo.edu.ucb.sis213.bl.ConnectionDB;

public class UsuariosDao {
    public void validarLogin(String alias, int pin) throws LoginException {
    String query = "SELECT id, saldo FROM usuarios WHERE pin = ? AND alias = ?";
    try {
        Connection connectionAux = ConnectionDB.getConnection();
        PreparedStatement preparedStatement = connectionAux.prepareStatement(query);
        preparedStatement.setInt(1, pin);
        preparedStatement.setString(2, alias);
        ResultSet resultSet = preparedStatement.executeQuery();   
        
        if (!resultSet.next()) {
            throw new LoginException("PIN o alias incorrecto");
        }
        
        // Resto del código para obtener los datos y realizar otras operaciones si es necesario.
    } catch (SQLException e) {
        // Manejo de excepciones específicas de SQL si es necesario.
        e.printStackTrace();
        throw new LoginException("Error en la consulta de autenticación");
    } catch (Exception e) {
        e.printStackTrace();
        throw new LoginException("Error en la autenticación");
    }    
}

    public double consultarSaldo(String alias) {
        String query = "SELECT saldo FROM usuarios WHERE alias = ?";
        try {
            Connection connectionAux = ConnectionDB.getConnection();
            PreparedStatement preparedStatement = connectionAux.prepareStatement(query);
            preparedStatement.setString(1, alias);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double saldoObtenido = resultSet.getDouble("saldo");
                resultSet.close();
                preparedStatement.close();
                return saldoObtenido;
            }
            resultSet.close();
            preparedStatement.close();
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al consultar el saldo.");
        }
        return -1;
    }

    public void realizarDeposito(double monto, String alias) {
        try {
            Connection connectionAux = ConnectionDB.getConnection();
            double cantidad = monto;
            String updateQuery = "UPDATE usuarios SET saldo = saldo + ? WHERE alias = ?";
            PreparedStatement updateStatement = connectionAux.prepareStatement(updateQuery);
            updateStatement.setDouble(1, cantidad);
            updateStatement.setString(2, alias);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected < 0) {
                throw new SQLException("Ocurrio un error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ocurrió un error al realizar el depósito.");
            //System.out.println("Ocurrió un error al realizar el depósito.");
        } catch(NumberFormatException e){
            e.printStackTrace();
            throw new RuntimeException("Ingrese un monto válido.");
            //JOptionPane.showMessageDialog(null, "Ingrese un monto válido.");
        }
    }

    public void realizarRetiro(double monto, String alias) {
        try {
            Connection connectionAux = ConnectionDB.getConnection();
            double cantidad = monto;
            String updateQuery = "UPDATE usuarios SET saldo = saldo - ? WHERE alias = ?";
            PreparedStatement updateStatement = connectionAux.prepareStatement(updateQuery);
            updateStatement.setDouble(1, cantidad);
            updateStatement.setString(2, alias);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected < 0) {
                throw new SQLException("Ocurrio un error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ocurrió un error al realizar el retiro.");
            //System.out.println("Ocurrió un error al realizar el depósito.");
        } catch(NumberFormatException e){
            e.printStackTrace();
            throw new RuntimeException("Ingrese un monto válido.");
            //JOptionPane.showMessageDialog(null, "Ingrese un monto válido.");
        }
    }
    
    public void cambiarPIN(int nuevoPin, String alias) {
        try {
            Connection connectionAux = ConnectionDB.getConnection();
            String updateQuery = "UPDATE usuarios SET pin = ? WHERE alias = ?";
            PreparedStatement updateStatement = connectionAux.prepareStatement(updateQuery);
            updateStatement.setInt(1, nuevoPin);
            updateStatement.setString(2, alias);
            int rowsAffected = updateStatement.executeUpdate();           
            if (rowsAffected < 0) {
                throw new SQLException("Ocurrio un error");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al realizar el cambio de Pin.");
        }
    }
}
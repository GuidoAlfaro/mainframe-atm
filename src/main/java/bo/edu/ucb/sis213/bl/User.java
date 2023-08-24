package bo.edu.ucb.sis213.bl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class User {
    protected int usuarioId;
    protected String alias;
    protected double saldo;
    protected int pinActual;
    protected Connection connection;
    
    public User(int usuarioId, String alias,double saldo, int pinActual, Connection connection) {
        this.usuarioId = usuarioId;
        this.alias = alias;
        this.saldo = saldo;
        this.pinActual = pinActual;
        this.connection = connection;
    }
    public User(int pinActual, String alias) {
        this.usuarioId = 0;
        this.alias = alias;
        this.saldo = 0;
        this.pinActual = pinActual;
        try {
            this.connection = ConnectionDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public int getPinActual() {
        return pinActual;
    }
    public void setPinActual(int pinActual) {
        this.pinActual = pinActual;
    }
    public Connection getConnection() {
        return connection;
    }
    public void setConnection() {
        try {
            this.connection = ConnectionDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validarPIN() {
        String query = "SELECT id, saldo FROM usuarios WHERE pin = ? and alias = ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, getPinActual());
            preparedStatement.setString(2, getAlias());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                setUsuarioId(resultSet.getInt("id"));
                setSaldo(resultSet.getDouble("saldo"));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(){
        if(validarPIN()){
            JOptionPane.showMessageDialog(null, "Bienvenido");
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "PIN incorrecto");
            return false;
        }
    }

    public double consultarSaldo() {
        String query = "SELECT saldo FROM usuarios WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, getUsuarioId());
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

    public void realizarDeposito(double monto) {
        try {
            double cantidad = monto;

            if (cantidad <= 0) {
                System.out.println("Cantidad no válida.");
            } else {
                String updateQuery = "UPDATE usuarios SET saldo = saldo + ? WHERE id = ?";
                PreparedStatement updateStatement = getConnection().prepareStatement(updateQuery);
                updateStatement.setDouble(1, cantidad);
                updateStatement.setInt(2, getUsuarioId());

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    historicoRegister(cantidad, "deposito");
                    setSaldo(getSaldo()+cantidad);
                    //System.out.println("Depósito realizado con éxito. Su nuevo saldo es: $" + saldo);
                    JOptionPane.showMessageDialog(null, "Depósito realizado con éxito. Su nuevo saldo es: $" + getSaldo());
                } else {
                    //System.out.println("No se realizó el depósito. Verifique su ID de usuario.");
                    JOptionPane.showMessageDialog(null, "No se realizó el depósito. Verifique su ID de usuario.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al realizar el depósito.");
        }
    }

    public void realizarRetiro(double monto) {
        
        try {
            double cantidad = monto;

            if (cantidad <= 0) {
                System.out.println("Cantidad no válida.");
            } else {
                String updateQuery = "UPDATE usuarios SET saldo = saldo - ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setDouble(1, cantidad);
                updateStatement.setInt(2, getUsuarioId());

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    historicoRegister(cantidad, "retiro");
                    setSaldo(getSaldo()-cantidad);
                    //System.out.println("Retiro realizado con éxito. Su nuevo saldo es: $" + saldo);
                    JOptionPane.showMessageDialog(null, "Retiro realizado con éxito. Su nuevo saldo es: $" + getSaldo());
                } else {
                    //System.out.println("No se realizó el retiro. Verifique su ID de usuario.");
                    JOptionPane.showMessageDialog(null, "No se realizó el retiro. Verifique su ID de usuario.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al realizar el retiro.");
        }
    }

    public void historicoRegister(double cantidad, String tipo_operacion){
        //insert query
        String insertQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad, fecha) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement insertStatement = getConnection().prepareStatement(insertQuery);
            insertStatement.setInt(1, getUsuarioId());
            insertStatement.setString(2, tipo_operacion);
            insertStatement.setDouble(3, cantidad);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
            insertStatement.setTimestamp(4, timestamp);

            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Se registro el "+tipo_operacion + " con exito.");
            } else {
                System.out.println("No se registro el "+tipo_operacion+".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al registrar el "+tipo_operacion+".");
        }
    }

    public void extractoBancario(Vector<Vector<Object>> data) {
        String query = "SELECT * FROM historico WHERE usuario_id = ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, getUsuarioId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("id"));
                row.add(resultSet.getString("tipo_operacion"));
                row.add(resultSet.getDouble("cantidad"));
                row.add(resultSet.getTimestamp("fecha"));
                data.add(row);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al consultar el extracto bancario.");
        }
    }

    public void cambiarPIN(int pinIngresado, int nuevoPin, int confirmacionPin) {
        if (pinIngresado == getPinActual()) {
            if (nuevoPin == confirmacionPin) {
                try {
                    String updateQuery = "UPDATE usuarios SET pin = ? WHERE id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setInt(1, nuevoPin);
                    updateStatement.setInt(2, getUsuarioId());
                    int rowsAffected = updateStatement.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        //System.out.println("El cambio de Pin fue exitoso");
                        JOptionPane.showMessageDialog(null, "El cambio de Pin fue exitoso");
                    } else {
                        //System.out.println("Ocurrio un error, vuelve a intentarlo");
                        JOptionPane.showMessageDialog(null, "Ocurrio un error, vuelve a intentarlo");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Ocurrió un error al realizar el cambio de Pin.");
                }
                //System.out.println("PIN actualizado con éxito.");
            } else {
                System.out.println("Los PINs no coinciden.");
                JOptionPane.showMessageDialog(null, "Los PINs no coinciden.");
            }
        } else {
            System.out.println("PIN incorrecto.");
            JOptionPane.showMessageDialog(null, "PIN incorrecto.");
        }
    }
}

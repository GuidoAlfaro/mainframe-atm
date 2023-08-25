package bo.edu.ucb.sis213.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import bo.edu.ucb.sis213.bl.ConnectionDB;

public class HistoricoDao {
    
    public void historicoRegister(double cantidad, String tipo_operacion, String alias) throws SQLException {
        // Obtener el ID del usuario basado en el alias
        String usuarioQuery = "SELECT id FROM usuarios WHERE alias = ?";
        int usuarioId = -1; // Valor por defecto si no se encuentra el ID
        
        try (Connection connectionAux = ConnectionDB.getConnection();
             PreparedStatement usuarioStatement = connectionAux.prepareStatement(usuarioQuery)) {
            
            usuarioStatement.setString(1, alias);
            try (ResultSet usuarioResultSet = usuarioStatement.executeQuery()) {
                if (usuarioResultSet.next()) {
                    usuarioId = usuarioResultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Ocurrió un error al registrar la operación.");
        }
        
        // Si no se encontró el ID, lanzar excepción
        if (usuarioId == -1) {
            throw new SQLException("No se encontró el usuario correspondiente al alias.");
        }
    
        // Insertar la operación en la tabla "historico"
        String insertQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad, fecha) VALUES (?, ?, ?, ?)";
        try (Connection connectionAux = ConnectionDB.getConnection();
             PreparedStatement insertStatement = connectionAux.prepareStatement(insertQuery)) {
    
            insertStatement.setInt(1, usuarioId);
            insertStatement.setString(2, tipo_operacion);
            insertStatement.setDouble(3, cantidad);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
            insertStatement.setTimestamp(4, timestamp);
    
            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("Ocurrió un error al registrar el " + tipo_operacion + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Ocurrió un error al registrar el " + tipo_operacion + ".");
        }
    }
    
    public void extractoBancario(Vector<Vector<Object>> data, String alias) throws SQLException {
        String usuarioQuery = "SELECT id FROM usuarios WHERE alias = ?";
        String historicoQuery = "SELECT * FROM historico WHERE usuario_id = ?";
        
        try (Connection connectionAux = ConnectionDB.getConnection();
             PreparedStatement usuarioStatement = connectionAux.prepareStatement(usuarioQuery);
             PreparedStatement historicoStatement = connectionAux.prepareStatement(historicoQuery)) {
            
            usuarioStatement.setString(1, alias);
            try (ResultSet usuarioResultSet = usuarioStatement.executeQuery()) {
                if (usuarioResultSet.next()) {
                    int usuarioId = usuarioResultSet.getInt("id");
                    
                    historicoStatement.setInt(1, usuarioId);
                    try (ResultSet historicoResultSet = historicoStatement.executeQuery()) {
                        while (historicoResultSet.next()) {
                            Vector<Object> row = new Vector<>();
                            row.add(historicoResultSet.getInt("id"));
                            row.add(historicoResultSet.getString("tipo_operacion"));
                            row.add(historicoResultSet.getDouble("cantidad"));
                            row.add(historicoResultSet.getTimestamp("fecha"));
                            data.add(row);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Ocurrió un error al consultar el extracto bancario.");
        }
    }    
}

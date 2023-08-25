package bo.edu.ucb.sis213.dto;

import java.sql.Connection;
import java.sql.SQLException;

import bo.edu.ucb.sis213.bl.ConnectionDB;

public class UsuarioDto {
    protected int usuarioId;
    protected String alias;
    protected double saldo;
    protected int pinActual;
    protected Connection connection;
    
    public UsuarioDto(int usuarioId, String alias,double saldo, int pinActual, Connection connection) {
        this.usuarioId = usuarioId;
        this.alias = alias;
        this.saldo = saldo;
        this.pinActual = pinActual;
        this.connection = connection;
    }
    public UsuarioDto(int pinActual, String alias) {
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
}

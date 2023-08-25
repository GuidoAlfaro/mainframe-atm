package bo.edu.ucb.sis213.bl;
import java.sql.SQLException;
import java.util.Vector;
import javax.security.auth.login.LoginException;
import bo.edu.ucb.sis213.dao.HistoricoDao;
import bo.edu.ucb.sis213.dao.UsuariosDao;

public class UserBl {

    public void validarPINBl(int pinIngresado, String aliasIngresado) throws LoginException{
        UsuariosDao usuariosDao = new UsuariosDao();
        usuariosDao.validarLogin(aliasIngresado, pinIngresado);
    }

    public void cambiarPIN(int pinActual, int pinIngresado, int nuevoPin, int confirmacionPin, String alias) {
        if (pinIngresado == pinActual) {
            if(nuevoPin == confirmacionPin){
                UsuariosDao usuariosDao = new UsuariosDao();
                usuariosDao.cambiarPIN(nuevoPin, alias);
            }else{
                throw new RuntimeException("Los PINs no coinciden.");
            }
        }else{
            throw new RuntimeException("PIN incorrecto.");
        }
    }

    static public double consultarSaldo(String alias) throws Exception{
        UsuariosDao usuariosDao = new UsuariosDao();
        return usuariosDao.consultarSaldo(alias);
    }

    public void realizarDeposito(double monto, String alias) throws RuntimeException{
        UsuariosDao usuariosDao = new UsuariosDao();
        usuariosDao.realizarDeposito(monto, alias);
    }

    public void realizarRetiro(double monto, String alias) throws RuntimeException{
        UsuariosDao usuariosDao = new UsuariosDao();
        usuariosDao.realizarRetiro(monto, alias);
    }

    public void historicoRegister(double cantidad, String tipo_operacion, String alias) throws SQLException {
        HistoricoDao historicoDao = new HistoricoDao();
        historicoDao.historicoRegister(cantidad, tipo_operacion, alias);
    }

    public void consultarHistorico(Vector<Vector<Object>> data, String alias) throws SQLException {
        HistoricoDao historicoDao = new HistoricoDao();
        historicoDao.extractoBancario(data, alias);
    }
}

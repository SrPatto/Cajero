
package cajero;

import java.sql.SQLException;
import javafx.scene.control.Alert;


public abstract class Usuario {
    protected int id;
    protected String num_cuenta;
    protected String password;
    protected Cuenta cuenta;
    
//    public String getNum_cuenta() {
//        return num_cuenta;
//    }
//    public void setNum_cuenta(String num_cuenta) {
//        this.num_cuenta = num_cuenta;
//    }
//    
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Cuenta getCuenta() {
//        return cuenta;
//    }
//    public void setCuenta(Cuenta cuenta) {
//        this.cuenta = cuenta;
//    }
}

class Cliente extends Usuario {  
    public UsuarioModel usuarioModel;
    
    public Cliente(String num_Cuenta, String password, Cuenta cuenta) throws SQLException, Exception {
        this.usuarioModel = new UsuarioModel();
        this.id = usuarioModel.getID_Usuario(num_Cuenta);
        this.num_cuenta = num_Cuenta;
        this.password = password;
        this.cuenta = cuenta;
    }
    
    public Cliente(String num_Cuenta, Cuenta cuenta) throws SQLException, Exception {
        this.usuarioModel = new UsuarioModel();
        this.id = usuarioModel.getID_Usuario(num_Cuenta);
        this.num_cuenta = num_Cuenta;
        this.cuenta = cuenta;
    }

    public void depositar(double cantidad) throws SQLException, Exception {
        // Lógica para depositar dinero
        this.usuarioModel = new UsuarioModel();
        
        double saldo = cuenta.getDinero();
        saldo += cantidad;
        usuarioModel.update_dinero(id, saldo);
        cuenta.setDinero(usuarioModel.getDinero(id));
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensaje del sistema");
                alert.setHeaderText(null);
                alert.setContentText("Depósito realizado con éxito.");
                alert.showAndWait();
    }

    public void retirar(double cantidad) throws SQLException, Exception {
        // Lógica para retirar dinero
        this.usuarioModel = new UsuarioModel();
        
        double saldo = cuenta.getDinero();
        if(cantidad<saldo){
            saldo -= cantidad;
            usuarioModel.update_dinero(id, saldo);
            cuenta.setDinero(usuarioModel.getDinero(id));
        
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensaje del sistema");
                alert.setHeaderText(null);
                alert.setContentText("Retiro realizado con éxito.");
                alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensaje del sistema");
                alert.setHeaderText(null);
                alert.setContentText("No tienes suficientes fondos");
                alert.showAndWait();
        }
        
    }

    public void transferir(double cantidad, Cuenta cuentaDestino) throws Exception {
        // Lógica para transferir dinero
        this.usuarioModel = new UsuarioModel();
        
        double saldo = cuenta.getDinero();
        double saldoDestino = cuentaDestino.getDinero();
        int idDestino = cuentaDestino.getId_usuario();
        
        if(usuarioModel.existCuenta(cuentaDestino.getNum_cuenta())){
            if(cantidad<saldo){
                saldo -= cantidad;
                saldoDestino += cantidad;
            
                usuarioModel.update_dinero(id, saldo);
                cuenta.setDinero(usuarioModel.getDinero(id));
            
                usuarioModel.update_dinero(idDestino, saldoDestino);
                cuentaDestino.setDinero(usuarioModel.getDinero(idDestino));
        
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensaje del sistema");
                alert.setHeaderText(null);
                alert.setContentText("Transferencia realizada con éxito.");
                alert.showAndWait();
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensaje del sistema");
                alert.setHeaderText(null);
                alert.setContentText("No tienes suficiente dinero para esta transferencia");
                alert.showAndWait();
            }
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("No existe el numero de cuenta.");
            alert.showAndWait();
        }
    }
}

class Admin extends Usuario {
    public Admin(String num_Cuenta, String password, Cuenta cuenta) {
        this.num_cuenta = num_Cuenta;
        this.password = password;
        this.cuenta = cuenta;
    }

    public void agregarUsuario() {
        // Lógica para agregar un usuario
    }

    public void eliminarUsuario() {
        // Lógica para eliminar un usuario
    }

    public void actualizarUsuario() {
        // Lógica para actualizar un usuario
    }
}
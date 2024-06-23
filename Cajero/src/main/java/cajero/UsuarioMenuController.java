package cajero;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuarioMenuController {
    
    private Stage stageMenu;
    private Login loginController;
    public UsuarioModel usuarioModel;
    
    @FXML private Button btn_Logout;
    @FXML private Button btn_DepositarToVentana;
    @FXML private Button btn_RetirarToVentana;
    @FXML private Button btn_TransferirToVentana;
    @FXML private TextField txtSaldo;

    
    @FXML
    void cerrarSesion(ActionEvent event) {
        loginController.limpiarCamposLogin();
        loginController.show();
        stageMenu.close();
    }
    
    @FXML
    public void init(String txtNumCuenta, String txtContrasenia, Stage stage, Login login) throws SQLException, Exception {
        this.loginController = login;
        this.stageMenu = stage;
        this.usuarioModel = new UsuarioModel();

        int idUsuario;
        try {
            idUsuario = usuarioModel.getID_Usuario(txtNumCuenta, txtContrasenia);
            Cuenta cuentaLogged = new Cuenta(idUsuario);
            Cliente userLogged = new Cliente(txtNumCuenta, txtContrasenia, cuentaLogged);
            
        } catch (SQLException e) {
            e.printStackTrace();

            throw e;
        }
        
        double saldo = usuarioModel.getDinero(idUsuario);
        txtSaldo.setText(String.valueOf(saldo));
    }
    
    @FXML
    void depositarToVentana(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Usuario/depositar.fxml"));
        Parent root = loader.load();
        DepositarController depositarController = loader.getController();
        depositarController.init(this, stageMenu); 
        Scene scene = new Scene(root);
        Stage stageDepositar = new Stage();
        stageDepositar.setScene(scene);
        depositarController.setStage(stageDepositar); 
        stageDepositar.show();
        stageMenu.close(); 
    }

    @FXML
    void retirarToVentana(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Usuario/retirar.fxml"));
        Parent root = loader.load();
        RetirarController retirarController = loader.getController();
        retirarController.init(this, stageMenu);
        Scene scene = new Scene(root);
        Stage stageRetirar = new Stage();
        stageRetirar.setScene(scene);
        retirarController.setStage(stageRetirar);
        stageRetirar.show();
        stageMenu.close();
    }

    @FXML
    void transferirToVentana(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Usuario/transferir.fxml"));
        Parent root = loader.load();
        TransferirController transferirController = loader.getController();
        transferirController.init(this, stageMenu);
        Scene scene = new Scene(root);
        Stage stageTransferir = new Stage();
        stageTransferir.setScene(scene);
        transferirController.setStage(stageTransferir);
        stageTransferir.show();
        stageMenu.close();
    }

    public void show() {
        stageMenu.show();
    }
}

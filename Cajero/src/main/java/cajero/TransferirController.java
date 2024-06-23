package cajero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransferirController {
    
    private UsuarioMenuController usuarioMenuController;
    private Stage stageBackToMenu;
    private Stage stageTransferir;
    private double saldo;
    private Cliente userLogged;
    private Cuenta cuentaLogged;

    @FXML private Button btn_BackToUsuarioMenu;
    @FXML private Button btn_Transferir;
    @FXML private TextField txtNumCuenta;
    @FXML private TextField txtTransferencia;
    
    public void init(Cliente userLogged, Cuenta cuentaLogged, UsuarioMenuController usuarioMenuController, Stage stageBackToMenu) {
        this.usuarioMenuController = usuarioMenuController;
        this.stageBackToMenu = stageBackToMenu;
        
        this.cuentaLogged = cuentaLogged; 
        
        if (cuentaLogged != null) {
            saldo = cuentaLogged.getDinero();
            this.userLogged = userLogged;
        }
    }

    void setStage(Stage stage) {
        this.stageTransferir = stage;
    }

    @FXML
    void BackToUsuarioMenu(ActionEvent event) {
        stageTransferir.close(); 
        usuarioMenuController.show(); 
    }

    @FXML
    void Transferir(ActionEvent event) {

    }

}

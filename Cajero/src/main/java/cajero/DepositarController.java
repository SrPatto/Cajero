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

public class DepositarController {
    
    private UsuarioMenuController usuarioMenuController;
    private Stage stageBackToMenu;
    private Stage stageDepositar;
    private double saldo;
    private Cliente userLogged;
    private Cuenta cuentaLogged;

    
    @FXML private Button btn_BackToUsuarioMenu;
    @FXML private Button btn_Depositar;
    @FXML private TextField txtDeposito;
    @FXML private TextField txtSaldo;
    
    public void init(Cliente userLogged, Cuenta cuentaLogged, UsuarioMenuController usuarioMenuController, Stage stageBackToMenu) {
        this.usuarioMenuController = usuarioMenuController;
        this.stageBackToMenu = stageBackToMenu;
        
        this.cuentaLogged = cuentaLogged; 
        
        if (cuentaLogged != null) {
            saldo = cuentaLogged.getDinero();
            txtSaldo.setText(String.valueOf(saldo));
            this.userLogged = userLogged;
        }
    }

    public void setStage(Stage stage) {
        this.stageDepositar = stage;
    }
    
    @FXML
    void backToUsuarioMenu(ActionEvent event) throws IOException {
        stageDepositar.close();
        usuarioMenuController.showSaldo();
        usuarioMenuController.show();
    }
    
    @FXML
    void Depositar(ActionEvent event) throws SQLException, Exception {
       double deposito = Double.parseDouble(txtDeposito.getText());
       userLogged.depositar(deposito);
       txtSaldo.setText(String.valueOf(cuentaLogged.getDinero()));
       txtDeposito.clear();
    }
}

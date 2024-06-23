package cajero;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RetirarController {
    private UsuarioMenuController usuarioMenuController;
    private Stage stageBackToMenu;
    private Stage stageRetirar;
    private double saldo;
    private Cliente userLogged;
    private Cuenta cuentaLogged;

    @FXML private Button btn_BackToUsuarioMenu;
    @FXML private Button btn_Retirar;
    @FXML private TextField txtRetirar;
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
        this.stageRetirar = stage;
    }
    
    @FXML
    void BackToUsuarioMenu(ActionEvent event) {
        stageRetirar.close(); 
        usuarioMenuController.showSaldo();
        usuarioMenuController.show(); 
    }
    
    @FXML
    void Retirar(ActionEvent event)throws SQLException, Exception {
       double retiro = Double.parseDouble(txtRetirar.getText());
       userLogged.retirar(retiro);
       txtSaldo.setText(String.valueOf(cuentaLogged.getDinero()));
       txtRetirar.clear();
    }

}

package cajero;

import java.io.IOException;
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
    
    @FXML private Button btn_BackToUsuarioMenu;
    @FXML private Button btn_Depositar;
    @FXML private TextField txtDeposito;
    @FXML private TextField txtSaldo;
    
    public void init(UsuarioMenuController usuarioMenuController, Stage stageBackToMenu) {
        this.usuarioMenuController = usuarioMenuController;
        this.stageBackToMenu = stageBackToMenu;
    }

    public void setStage(Stage stage) {
        this.stageDepositar = stage;
    }
    
    @FXML
    void backToUsuarioMenu(ActionEvent event) throws IOException {
        stageDepositar.close(); 
        usuarioMenuController.show(); 
    }
    
    @FXML
    void Depositar(ActionEvent event) {
       
    }
}

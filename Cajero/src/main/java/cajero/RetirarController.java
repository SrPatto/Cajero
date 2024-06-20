package cajero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RetirarController {
    private UsuarioMenuController usuarioMenuController;
    private Stage stageBackToMenu;
    private Stage stageRetirar;

    @FXML private Button btn_BackToUsuarioMenu;
    @FXML private Button btn_Retirar;
    @FXML private TextField txtRetirar;
    @FXML private TextField txtSaldo;
    
    public void init(UsuarioMenuController usuarioMenuController, Stage stageBackToMenu) {
        this.usuarioMenuController = usuarioMenuController;
        this.stageBackToMenu = stageBackToMenu;
    }

    public void setStage(Stage stage) {
        this.stageRetirar = stage;
    }
    
    @FXML
    void BackToUsuarioMenu(ActionEvent event) {
        stageRetirar.close(); 
        usuarioMenuController.show(); 
    }
    
    @FXML
    void Retirar(ActionEvent event) {
        
    }

}

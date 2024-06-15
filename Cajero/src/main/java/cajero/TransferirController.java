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

    @FXML
    private Button btn_BackToUsuarioMenu;

    @FXML
    private Button btn_Transferir;

    @FXML
    private TextField txtNumCuenta;

    @FXML
    private TextField txtTransferencia;

    @FXML
    void BackToUsuarioMenu(ActionEvent event) {
        stageTransferir.close(); 
        usuarioMenuController.show(); 
    }

    void init(UsuarioMenuController usuarioMenuController, Stage stageBackToMenu) {
        this.usuarioMenuController = usuarioMenuController;
        this.stageBackToMenu = stageBackToMenu;
        
    }

    void setStage(Stage stage) {
        this.stageTransferir = stage;
    }
    
    @FXML
    void Transferir(ActionEvent event) {

    }

}

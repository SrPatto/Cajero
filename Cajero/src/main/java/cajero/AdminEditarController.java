package cajero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminEditarController {

    @FXML private Button btn_CerrarVentanaEditar;
    @FXML private Button btn_EditarUsuario;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtNumCuenta;
    @FXML private TextField txtSaldo;
    
    private AdminUsuariosController adminUsuariosController;
    private Stage stageEditarUsuario;
    
    void init(AdminUsuariosController adminUsuariosController, Stage stageEditarUsuario) {
        this.adminUsuariosController = adminUsuariosController;
        this.stageEditarUsuario = stageEditarUsuario;
    }

    void setStage(Stage stageEditarUsuario) {
        this.stageEditarUsuario = stageEditarUsuario;
    }

    @FXML
    void cerrarVentanaEditar(ActionEvent event) {
        stageEditarUsuario.close();
    }

    @FXML
    void editarUsuario(ActionEvent event) {
       
    }
}

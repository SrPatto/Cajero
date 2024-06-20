package cajero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminAgregarController {

    @FXML private Button btn_AgregarUsuario;
    @FXML private Button btn_CerrarVentanaAgregar;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtNumCuenta;
    @FXML private TextField txtSaldo;

    private AdminUsuariosController adminUsuariosController;
    private Stage stageAgregarUsuario;

    void init(AdminUsuariosController adminUsuariosController, Stage stageAdminUsuarios) {
        this.adminUsuariosController = adminUsuariosController;
        this.stageAgregarUsuario = stageAdminUsuarios;
    }

    
    void setStage(Stage stageAgregarUsuario) {
        this.stageAgregarUsuario = stageAgregarUsuario;
    }

    @FXML
    void agregarUsuario(ActionEvent event) {
        
    }

    @FXML
    void cerrarVentanaAgregar(ActionEvent event) {
        stageAgregarUsuario.close();  
    }
}

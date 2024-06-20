package cajero;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminUsuariosController implements Initializable {

    private Stage stageAdminUsuarios;
    private Login loginController;

    @FXML private Button btn_ActualizarTabla;
    @FXML private Button btn_AgregarUsuario;
    @FXML private Button btn_EditarUsuario;
    @FXML private Button btn_EliminarUsuario;
    @FXML private Button btn_logout;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    void init(String txtNumCuenta, String txtContrasenia, Stage stage, Login login) {
        this.loginController = login;
        this.stageAdminUsuarios = stage;
    }    

    @FXML
    void actualizarTabla(ActionEvent event) {
        
    }

    @FXML
    void agregarUsuario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Admin/adminAgregar.fxml"));
        Parent root = loader.load();
        AdminAgregarController adminAgregarController = loader.getController();
        adminAgregarController.init(this, stageAdminUsuarios);
        Scene scene = new Scene(root);
        Stage stageAgregarUsuario = new Stage();
        stageAgregarUsuario.setScene(scene);
        adminAgregarController.setStage(stageAgregarUsuario);
        stageAgregarUsuario.show();
    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        loginController.limpiarCamposLogin();
        loginController.show();
        stageAdminUsuarios.close();
    }

    @FXML
    void editarUsuario(ActionEvent event) {
        
    }

    @FXML
    void eliminarUsuario(ActionEvent event) {
        
    }
}

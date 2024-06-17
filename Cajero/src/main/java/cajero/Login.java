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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class Login implements Initializable {
    public LoginModel loginModel = new LoginModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        if(loginModel.isDBConnected()){
//            
//        }
    }
    
    private Stage stageLogin;
    
    @FXML
    private TextField txtNumCuenta;
    
    @FXML
    private TextField txtContrasenia;
    
    @FXML
    void login(ActionEvent event) throws IOException {
        if(txtNumCuenta.getText().equals("user") && txtContrasenia.getText().equals("user")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Usuario/usuarioMenu.fxml"));
            Parent root = loader.load();
            UsuarioMenuController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
        
            controller.init(txtNumCuenta.getText(), txtContrasenia.getText(), stage, this);
            stage.show();
            stageLogin.close(); 
        }
        else{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Numero de Cuenta o Contraseña incorrecta");
            alert.showAndWait();
            
            limpiarCamposLogin();
        }
        
    }
    void limpiarCamposLogin() {
        txtNumCuenta.clear();
        txtContrasenia.clear();
    }

    void setStage(Stage stage) {
        stageLogin = stage;
    }

    void show() {
        stageLogin.show();
    }


    
}



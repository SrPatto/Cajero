package cajero;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Stage stageLogin;
    @FXML private TextField txtNumCuenta;
    @FXML private TextField txtContrasenia;
    public LoginModel loginModel = new LoginModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if(loginModel.isDBConnected()){
                System.out.println("Conectado");
            }else{
                System.out.println("No Conectado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void login(ActionEvent event) throws IOException{
        try{
            if(loginModel.isLogin(txtNumCuenta.getText(), txtContrasenia.getText())){
                if(loginModel.isAdmin(txtNumCuenta.getText(), txtContrasenia.getText())){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Admin/adminUsuarios.fxml"));
                    Parent root = loader.load();
                    AdminUsuariosController controller = loader.getController();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
            
                    controller.init(txtNumCuenta.getText(), txtContrasenia.getText(), stage, this);
                    stage.show();
                    stageLogin.close();
                }
                else{
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
            }
            else{    
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Mensaje del sistema");
                alert.setHeaderText(null);
                alert.setContentText("Numero de Cuenta o Contraseña incorrecta");
                alert.showAndWait();
            
                limpiarCamposLogin();
            } 
        } catch (SQLException e){
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



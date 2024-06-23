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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
    private Stage stageLogin;
    @FXML private TextField txtNumCuenta;
    @FXML private TextField txtContrasenia;
    public LoginModel loginModel;

    public Login() throws Exception {
        this.loginModel = new LoginModel();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if(loginModel.isDBConnected()){
                System.out.println("Conectado");
            }else{
                System.out.println("No Conectado");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos", ex);
        }
    }
    
    @FXML
    void login(ActionEvent event) throws Exception {
        if (txtNumCuenta.getText().isEmpty() || txtContrasenia.getText().isEmpty()) {
            showAlert("Error de validación", "Numero de Cuenta y Contraseña son requeridos.");
            return;
        }
        
        try {
            if (loginModel.isLogin(txtNumCuenta.getText(), txtContrasenia.getText())) {
                String fxmlFile = loginModel.isAdmin(txtNumCuenta.getText(), txtContrasenia.getText()) ? "/cajero/Admin/adminUsuarios.fxml" : "/cajero/Usuario/usuarioMenu.fxml";
                                
                loadAndShowScene(fxmlFile);
            } else {
                showAlert("Mensaje del sistema", "Numero de Cuenta o Contraseña incorrecta");
                limpiarCamposLogin();
            }
        } catch (SQLException | IOException e) {
            LOGGER.log(Level.SEVERE, "Error durante el proceso de login", e);
            showAlert("Error del sistema", "Ocurrió un error al intentar iniciar sesión.");
            limpiarCamposLogin();
        }
    }
    
    private void loadAndShowScene(String fxmlFile) throws IOException, SQLException, Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        if(loginModel.isAdmin(txtNumCuenta.getText(), txtContrasenia.getText())){
            AdminUsuariosController controller = loader.getController();
            controller.init(txtNumCuenta.getText(), txtContrasenia.getText(), stage, this);
        }
        else{
            UsuarioMenuController controller = loader.getController();
            controller.init(txtNumCuenta.getText(), txtContrasenia.getText(), stage, this);
        }
        
        stage.show();
        stageLogin.close();
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    void limpiarCamposLogin() {
        txtNumCuenta.clear();
        txtContrasenia.clear();
    }

    void setStage(Stage stage) {
        this.stageLogin = stage;
    }

    void show() {
        stageLogin.show();
    }
}
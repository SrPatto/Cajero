/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cajero;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


public class AdminUsuariosController implements Initializable {

    private Stage stageAdminUsuarios;
    private Login loginController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    void init(String txtNumCuenta, String txtContrasenia, Stage stage, Login login) {
        this.loginController = login;
        this.stageAdminUsuarios = stage;
    }    
    
}

/*package cajero;

import java.io.IOException;
import javafx.fxml.FXML;

public class Login {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}*/
package cajero;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class Login {
    private Stage stagep;
    
    @FXML
    private TextField txtNumCuenta;
    
    @FXML
    private TextField txtContrasenia;
    
    @FXML
    void login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Usuario/usuarioMenu.fxml"));
        Parent root = loader.load();
        UsuarioMenuController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        
        controller.init(txtNumCuenta.getText(), txtContrasenia.getText(), stage, this);
        stage.show();
        stagep.close(); 
    }

    void setStage(Stage stage) {
        stagep = stage;
    }

    void show() {
        stagep.show();
    }
}



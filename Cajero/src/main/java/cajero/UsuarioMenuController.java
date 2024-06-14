package cajero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class UsuarioMenuController {
    private Login loginController;
    private Stage stage;
    @FXML
    void cerrarSesion(ActionEvent event) {
        loginController.show();
        stage.close();
    }

    void init(String txtNumCuenta, String txtContrasenia, Stage stage, Login login) {
        this.loginController = login;
        this.stage = stage;
    }

    

}

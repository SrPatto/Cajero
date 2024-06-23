package cajero;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

public class DepositarController {
    
    private UsuarioMenuController usuarioMenuController;
    private Stage stageBackToMenu;
    private Stage stageDepositar;
    private double saldo;
    private Cliente userLogged;
    private Cuenta cuentaLogged;
    
    @FXML private TextInputControl txtDeposito;
    @FXML private TextField txtSaldo;
    
    public void init(Cliente userLogged, Cuenta cuentaLogged, UsuarioMenuController usuarioMenuController, Stage stageBackToMenu) {
        this.usuarioMenuController = usuarioMenuController;
        this.stageBackToMenu = stageBackToMenu;
        
        this.cuentaLogged = cuentaLogged; 
        
        if (cuentaLogged != null) {
            saldo = cuentaLogged.getDinero();
            txtSaldo.setText(String.valueOf(saldo));
            this.userLogged = userLogged;
        }
    }

    public void setStage(Stage stage) {
        this.stageDepositar = stage;
    }
    
    @FXML
    void backToUsuarioMenu(ActionEvent event) throws IOException {
        stageDepositar.close();
        usuarioMenuController.showSaldo();
        usuarioMenuController.show();
    }
    
    @FXML
    void Depositar(ActionEvent event) throws SQLException, Exception {
        String depositoStr = txtDeposito.getText().trim();
        double deposito = Double.parseDouble(depositoStr);

        if (!esNumeroValido(depositoStr)) {
            mostrarAlerta("Error de validación", "Ingrese un número válido para el depósito.");
            txtDeposito.clear();
            return;
        }

        if (deposito > 9000) {
            mostrarAlerta("Error en el deposito", "Solo puedes depositar un máximo de $9,000 pesos.");
            txtDeposito.clear();
            return;
        }

        userLogged.depositar(deposito);
        txtSaldo.setText(String.valueOf(cuentaLogged.getDinero()));
        txtDeposito.clear(); 
    }
    
    private boolean esNumeroValido(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

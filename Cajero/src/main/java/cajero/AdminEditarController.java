package cajero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminEditarController {
    private Connection connection;
    private int id_usuario;
    private String nombre;
    private String num_cuenta;
    private double dinero;
    private static final String DB = "CajeroDB.db";

    @FXML private Button btn_CerrarVentanaEditar;
    @FXML private Button btn_EditarUsuario;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtNumCuenta;
    @FXML private TextField txtSaldo;
    
    private AdminUsuariosController adminUsuariosController;
    private Stage stageEditarUsuario;
    
    void init(AdminUsuariosController adminUsuariosController, Stage stageEditarUsuario, Cuenta cuentaEditar) {
        this.adminUsuariosController = adminUsuariosController;
        this.stageEditarUsuario = stageEditarUsuario;
        
        txtNombreUsuario.setText(cuentaEditar.getNombre());
        txtNumCuenta.setText(cuentaEditar.getNum_cuenta());
        txtSaldo.setText(String.valueOf(cuentaEditar.getDinero()));
        
        id_usuario = cuentaEditar.getId_usuario();
    }

    void setStage(Stage stageEditarUsuario) {
        this.stageEditarUsuario = stageEditarUsuario;
    }
    
        private void editarUsuario(Connection conn, String nombre, String num_cuenta, double dinero, int id_usuario) throws SQLException {
        String editarUsuarioQuery = "UPDATE Usuarios SET num_cuenta = ? WHERE id = ?";
        String editarCuentaQuery = "UPDATE Cuentas SET num_cuenta = ?, nombre = ?, dinero = ? WHERE id_usuario = ?";
                
        try (PreparedStatement pstmtUsuario = conn.prepareStatement(editarUsuarioQuery);
            PreparedStatement pstmtCuenta = conn.prepareStatement(editarCuentaQuery)) {

            // Modificar Datos de Usuario
            pstmtUsuario.setString(1, num_cuenta);
            pstmtUsuario.setInt(2, id_usuario);
            pstmtUsuario.executeUpdate();
 
            // Modificar datos de Cuenta
            pstmtCuenta.setString(1, num_cuenta);
            pstmtCuenta.setString(2, nombre);
            pstmtCuenta.setDouble(3, dinero);
            pstmtCuenta.setInt(4, id_usuario);
            pstmtCuenta.executeUpdate();
        }
        catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }

    @FXML
    void cerrarVentanaEditar(ActionEvent event) {
        stageEditarUsuario.close();
    }

    @FXML
    void editarUsuario(ActionEvent event) {
        nombre = txtNombreUsuario.getText();
        num_cuenta = txtNumCuenta.getText();
        dinero = Double.parseDouble(txtSaldo.getText());
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB);
            Statement stmt = conn.createStatement()) {
            editarUsuario(conn, nombre, num_cuenta, dinero, id_usuario);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Usuario modificado con exito.");
            alert.showAndWait();
            
            stageEditarUsuario.close();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Error al modificar usuario.");
            System.out.println(ex.getMessage());
            
            stageEditarUsuario.close();
        }
    }
}

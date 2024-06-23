package cajero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.control.Alert;

public class AdminAgregarController {
    private Connection connection;
    private int id_usuario;
    private String nombre;
    private String num_cuenta;
    private double dinero;
    private static final String DB = "CajeroDB.db";


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
    
    private void insertarUsuario(Connection conn, String nombre, String num_cuenta, double dinero) {
        int numeroAleatorio = (int)(Math.random() * 10000) + 1;
        String contrasenia = String.valueOf(numeroAleatorio);
  
        String insertarUsuarioQuery = "INSERT INTO Usuarios (num_cuenta, password, admin) VALUES (?, ?, ?)";
        String insertarCuentaQuery = "INSERT INTO Cuentas (num_cuenta, nombre, dinero, id_usuario) VALUES (?, ?, ?, ?)";
                
        try (PreparedStatement pstmtUsuario = conn.prepareStatement(insertarUsuarioQuery, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstmtCuenta = conn.prepareStatement(insertarCuentaQuery)) {

            // Insertar Datos de Usuario
            pstmtUsuario.setString(1, num_cuenta);
            pstmtUsuario.setString(2, contrasenia);
            pstmtUsuario.setBoolean(3, false);
            pstmtUsuario.executeUpdate();
            ResultSet generatedKeys = pstmtUsuario.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                System.out.println("user ID: " + userId);
                System.out.println("user password: " + contrasenia);

                // Insertar datos de Cuenta
                pstmtCuenta.setString(1, num_cuenta);
                pstmtCuenta.setString(2, nombre);
                pstmtCuenta.setDouble(3, dinero);
                pstmtCuenta.setInt(4, userId);
                pstmtCuenta.executeUpdate();
            }
            
            
        }
        catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
    void limpiarCamposLogin() {
        txtNombreUsuario.clear();
        txtNumCuenta.clear();
        txtSaldo.clear();
    }

    @FXML
    void agregarUsuario(ActionEvent event) {
        nombre = txtNombreUsuario.getText();
        num_cuenta = txtNumCuenta.getText();
        dinero = Integer.parseInt(txtSaldo.getText());
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB);
            Statement stmt = conn.createStatement()) {
            insertarUsuario(conn, nombre, num_cuenta, dinero);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Usuario ingresado con exito.");
            alert.showAndWait();
            
            stageAgregarUsuario.close();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Error al ingresar usuario.");
            System.out.println(ex.getMessage());
            
            limpiarCamposLogin();
        }
    }

    @FXML
    void cerrarVentanaAgregar(ActionEvent event) {
        stageAgregarUsuario.close();  
    }
}

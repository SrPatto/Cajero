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

    private void insertarUsuario(Connection conn, String nombre, String num_cuenta, double dinero) throws SQLException {
        String contrasenia = generarContraseniaAleatoria();

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
        } catch (SQLException ex) {
            throw ex;
        }
    }

    private boolean verificarNumCuentaDuplicado(Connection conn, String num_cuenta) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM Usuarios WHERE num_cuenta = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, num_cuenta);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @FXML
    void cerrarVentanaAgregar(ActionEvent event) {
        stageAgregarUsuario.close();  
    }

    @FXML
    void agregarUsuario(ActionEvent event) {
        String NameUserStr = txtNombreUsuario.getText().trim();
        String NumCuentaStr = txtNumCuenta.getText().trim();
        String SaldoStr = txtSaldo.getText().trim();

        if (!NombreValido(NameUserStr) || !NumeroValido(NumCuentaStr) || !SaldoValido(SaldoStr)) {
            mostrarAlerta("Error de validación", "Ingrese datos válidos para todos los campos.");
            limpiarCampos();
            return;
        }

        if (NumCuentaStr.length() != 10) {
            mostrarAlerta("Error de validación", "El número de cuenta debe tener exactamente 10 dígitos.");
            txtNumCuenta.clear();
            return;
        }

        String nombre = txtNombreUsuario.getText();
        String num_cuenta = txtNumCuenta.getText();
        double dinero = Double.parseDouble(txtSaldo.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB)) {
            if (verificarNumCuentaDuplicado(conn, num_cuenta)) {
                mostrarAlerta("Error de validación", "El número de cuenta ya existe. Ingrese un número de cuenta diferente.");
                txtNumCuenta.clear();
                return;
            }

            insertarUsuario(conn, nombre, num_cuenta, dinero);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Usuario ingresado con éxito.");
            alert.showAndWait();

            stageAgregarUsuario.close();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Error al ingresar usuario.");
            alert.showAndWait();
            System.out.println("Ocurrió un error: " + ex.getMessage());

            limpiarCampos();
        }
    }

    void limpiarCampos() {
        txtNombreUsuario.clear();
        txtNumCuenta.clear();
        txtSaldo.clear();
    }

    private boolean NombreValido(String nombre) {
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s'\\-]+");
    }

    private boolean NumeroValido(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean SaldoValido(String saldo) {
        try {
            double valor = Double.parseDouble(saldo);
            return valor >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String generarContraseniaAleatoria() {
        int numeroAleatorio = (int)(Math.random() * 10000) + 1;
        return String.valueOf(numeroAleatorio);
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

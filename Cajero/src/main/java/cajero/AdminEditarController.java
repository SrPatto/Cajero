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
    private Connection conn;
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
    
    void setStage(Stage stageEditarUsuario) {
        this.stageEditarUsuario = stageEditarUsuario;
    }
    
    void init(AdminUsuariosController adminUsuariosController, Stage stageEditarUsuario, Cuenta cuentaEditar) {
        this.adminUsuariosController = adminUsuariosController;
        this.stageEditarUsuario = stageEditarUsuario;

        // Inicializar la conexión a la base de datos
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + DB);
        } catch (SQLException ex) {
            mostrarAlerta("Error de conexión", "No se pudo conectar a la base de datos.");
            ex.printStackTrace();
        }

        if (cuentaEditar == null) {
            // Mostrar alerta de que no se ha seleccionado un usuario
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Por favor seleccione un usuario para editar.");
            alert.showAndWait();
            return; 
        }
        
        this.nombre = cuentaEditar.getNombre();
        this.num_cuenta = cuentaEditar.getNum_cuenta();
        this.dinero = cuentaEditar.getDinero();
        this.id_usuario = cuentaEditar.getId_usuario();

        // Establecer los valores
        txtNombreUsuario.setText(nombre);
        txtNumCuenta.setText(num_cuenta);
        txtSaldo.setText(String.valueOf(dinero));
    }
    
    private void editarUsuario(String nombre, String num_cuenta, double dinero, int id_usuario) throws SQLException {
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
        catch (SQLException ex) {
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
        // Verificar si solo queda el administrador en la base de datos
        try {
            if (esAdmin() == 0) {
                mostrarAlerta("No hay usuarios para editar", "No hay usuarios disponibles para editar.");
                return;
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error de base de datos", "Error al contar los usuarios.");
            ex.printStackTrace();
            return;
        }

        String nombre = txtNombreUsuario.getText().trim();
        String num_cuenta = txtNumCuenta.getText().trim();
        String saldoText = txtSaldo.getText().trim();

        if (!NombreValido(nombre) || !NumeroValido(num_cuenta) || !SaldoValido(saldoText)) {
            mostrarAlerta("Error de validación", "Ingrese datos válidos para todos los campos.");
            return;
        }

        if (num_cuenta.length() != 10) {
            mostrarAlerta("Error de validación", "El número de cuenta debe tener exactamente 10 dígitos.");
            return;
        }

        double dinero;
        try {
            dinero = Double.parseDouble(saldoText);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de validación", "Ingrese un valor numérico válido para el saldo.");
            return;
        }

        try {
            if (verificarNumCuentaDuplicado(num_cuenta, id_usuario)) {
                mostrarAlerta("Error de validación", "El número de cuenta ya existe. Ingrese un número de cuenta diferente.");
                return;
            }

            // Actualización del usuario y su cuenta
            editarUsuario(nombre, num_cuenta, dinero, id_usuario);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Usuario modificado con éxito.");
            alert.showAndWait();

            stageEditarUsuario.close();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Error al modificar usuario.");
            alert.showAndWait();
            System.out.println("Ocurrió un error: " + ex.getMessage());
        }
    }

    private int esAdmin() throws SQLException {
        String consulta = "SELECT COUNT(*) FROM Usuarios WHERE id != 1"; 
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }


    private boolean verificarNumCuentaDuplicado(String num_cuenta, int id_usuario) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM Usuarios WHERE num_cuenta = ? AND id != ?";
        try (PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, num_cuenta);
            pstmt.setInt(2, id_usuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
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

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

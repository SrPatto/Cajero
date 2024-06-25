package cajero;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminUsuariosController implements Initializable {

    private Stage stageAdminUsuarios;
    private Login loginController;
    private Connection connection;
    public UsuarioModel usuarioModel;
    private Admin userLogged;
    private Cuenta cuentaLogged;
    private int idUsuario;
    private String nombre;
    private String num_cuenta;
    private double saldo;
    private ObservableList<Cuenta> clientes;
    private Cuenta cuentaSeleccionada;
    

    @FXML private Button btn_ActualizarTabla;
    @FXML private Button btn_AgregarUsuario;
    @FXML private Button btn_EditarUsuario;
    @FXML private Button btn_EliminarUsuario;
    @FXML private Button btn_logout;
    @FXML private TableView<Cuenta> tbl_Usuarios;
    @FXML private TableColumn<Cuenta, String> colID;
    @FXML private TableColumn<Cuenta, String> colNombre;
    @FXML private TableColumn<Cuenta, String> colNoCuenta;
    @FXML private TableColumn<Cuenta, String> colSaldo;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    void init(String txtNumCuenta, String txtContrasenia, Stage stage, Login login) throws Exception {
        this.loginController = login;
        this.stageAdminUsuarios = stage;
        this.usuarioModel = new UsuarioModel();
        colID.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNoCuenta.setCellValueFactory(new PropertyValueFactory<>("num_cuenta"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("dinero"));

        try {
            idUsuario = usuarioModel.getID_Usuario(txtNumCuenta);
            cuentaLogged = new Cuenta(idUsuario);
            userLogged = new Admin(txtNumCuenta, txtContrasenia, cuentaLogged);
        
        } catch (SQLException e) {
                e.printStackTrace();
                throw e;
        }

        cargarDatos();
        
        tbl_Usuarios.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Cuenta>) c -> {
            if (c.getList().size() == 1) { 
                cuentaSeleccionada = c.getList().get(0);
                nombre = cuentaSeleccionada.getNombre();
                num_cuenta = cuentaSeleccionada.getNum_cuenta();
                saldo = cuentaSeleccionada.getDinero(); 
            }
        });
        
    }    
    
    private void cargarDatos() throws Exception {
        clientes = FXCollections.observableArrayList();
        String query = "SELECT id_usuario, nombre, num_cuenta, dinero FROM Cuentas WHERE id_usuario != ?";

        try {
            connection = SqliteConnection.connect(); 
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_usuario = Integer.parseInt(resultSet.getString("id_usuario"));
                String nombre = resultSet.getString("nombre");
                String num_cuenta = resultSet.getString("num_cuenta");
                double saldo = Double.parseDouble(resultSet.getString("dinero"));
                clientes.add(new Cuenta(id_usuario, nombre, num_cuenta, saldo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        tbl_Usuarios.setItems(clientes);

    }

    @FXML
    void actualizarTabla(ActionEvent event) throws Exception {
        cargarDatos(); 
    }

    @FXML
    void agregarUsuario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Admin/adminAgregar.fxml"));
        Parent root = loader.load();
        AdminAgregarController adminAgregarController = loader.getController();
        adminAgregarController.init(this, stageAdminUsuarios);
        Scene scene = new Scene(root);
        Stage stageAgregarUsuario = new Stage();
        stageAgregarUsuario.setScene(scene);
        adminAgregarController.setStage(stageAgregarUsuario);
        stageAgregarUsuario.show();
    }
    
    @FXML
    void editarUsuario(ActionEvent event) {
        if (cuentaSeleccionada == null) {
            mostrarAlerta("Editar Usuario", "Error", "Por favor, seleccione un usuario para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cajero/Admin/adminEditar.fxml"));
            Parent root = loader.load();
            AdminEditarController adminEditarController = loader.getController();
            adminEditarController.init(this, stageAdminUsuarios, cuentaSeleccionada);
            Scene scene = new Scene(root);
            Stage stageEditarUsuario = new Stage();
            stageEditarUsuario.setScene(scene);
            adminEditarController.setStage(stageEditarUsuario);
            stageEditarUsuario.show();
            
            cuentaSeleccionada = null;
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Editar Usuario", "Error", "No se pudo abrir la ventana de edición.");
        }
    }


    @FXML
    void cerrarSesion(ActionEvent event) {
        loginController.limpiarCamposLogin();
        loginController.show();
        stageAdminUsuarios.close();
    }

    

   @FXML
void eliminarUsuario(ActionEvent event) {
    if (cuentaSeleccionada == null) {
        mostrarAlerta("Eliminar Usuario", "Error", "Por favor, seleccione un usuario para eliminar.");
        return;
    }

    try {
        System.out.println("Intentando eliminar el usuario...");
        // Llamar al método para eliminar el usuario
        userLogged.eliminarUsuario(cuentaSeleccionada);
        System.out.println("Usuario eliminado de la base de datos.");
        
        // Eliminar el usuario de la lista de clientes
        clientes.remove(cuentaSeleccionada);
        System.out.println("Usuario eliminado de la lista de clientes.");
       
    } catch (Exception e) {
        // Mostrar mensaje de error en caso de excepción
        mostrarAlerta("Eliminar Usuario", "Error", "Ocurrió un error al eliminar el usuario.");
        e.printStackTrace();
    } finally {
        cuentaSeleccionada = null; // Limpiar la selección en cualquier caso
        
    }
}


    
    private void mostrarAlerta(String titulo, String encabezado, String contenido) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(titulo);
    alert.setHeaderText(encabezado);
    alert.setContentText(contenido);
    alert.showAndWait();
    }
}

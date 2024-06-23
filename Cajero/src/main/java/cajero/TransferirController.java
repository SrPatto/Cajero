package cajero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TransferirController {
    
    private UsuarioMenuController usuarioMenuController;
    private Stage stageBackToMenu;
    private Stage stageTransferir;
    private double saldo;
    private Cliente userLogged;
    private Cuenta cuentaLogged;
    private Connection connection;
    public UsuarioModel usuarioModel;
    private Cuenta cuentaDestino;
    private ObservableList<Cuenta> clientes;
    private int ID_cuentaLogged;


    @FXML private Button btn_BackToUsuarioMenu;
    @FXML private Button btn_Transferir;
    @FXML private TextField txtNumCuenta;
    @FXML private TextField txtTransferencia;
    @FXML
    private TableView<Cuenta> tbl_Transferir;
    @FXML
    private TableColumn<Cuenta, String> colNombre;
    @FXML
    private TableColumn<Cuenta, String> colNoCuenta;

    public void init(Cliente userLogged, Cuenta cuentaLogged, UsuarioMenuController usuarioMenuController, Stage stageBackToMenu) throws SQLException, Exception {
        this.usuarioMenuController = usuarioMenuController;
        this.stageBackToMenu = stageBackToMenu;
        this.usuarioModel = new UsuarioModel();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNoCuenta.setCellValueFactory(new PropertyValueFactory<>("num_cuenta"));

        ID_cuentaLogged = cuentaLogged.getId_usuario();
        
        cargarDatos();
        
        tbl_Transferir.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Cuenta>) c -> {
            if (c.getList().size() == 1) { 
                Cuenta cuentaSeleccionada = c.getList().get(0);
                txtNumCuenta.setText(cuentaSeleccionada.getNum_cuenta()); 
            }
        });

        this.cuentaLogged = cuentaLogged;

        if (cuentaLogged != null) {
            saldo = cuentaLogged.getDinero();
            this.userLogged = userLogged;
        }
    }

    void setStage(Stage stage) {
        this.stageTransferir = stage;
    }

    private void cargarDatos() throws Exception {
        clientes = FXCollections.observableArrayList();
        String query = "SELECT nombre, num_cuenta FROM Cuentas WHERE id_usuario != ?";

        try {
            connection = SqliteConnection.connect(); 
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID_cuentaLogged);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String num_cuenta = resultSet.getString("num_cuenta");
                clientes.add(new Cuenta(nombre, num_cuenta));
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

        tbl_Transferir.setItems(clientes);
    }
            
    
    
    @FXML
    void BackToUsuarioMenu(ActionEvent event) {
        stageTransferir.close();
        usuarioMenuController.showSaldo();
        usuarioMenuController.show();
    }

    @FXML
    void Transferir(ActionEvent event) throws Exception {
        String transferenciaStr = txtTransferencia.getText().trim();
        
        if(!esNumeroValido(transferenciaStr)){
            mostrarAlerta("Error de validación", "Ingrese un número válido para la transferencia.");
            txtTransferencia.clear();
            txtNumCuenta.clear();
            return;
        }
        
        double transferencia = Double.parseDouble(txtTransferencia.getText());
        String num_cuentaDestino = txtNumCuenta.getText();
        
        cuentaDestino = new Cuenta(num_cuentaDestino);
        userLogged.transferir(transferencia, cuentaDestino);
        txtTransferencia.clear();
        txtNumCuenta.clear();
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
package cajero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UsuarioModel {
    private Connection connection;

    public UsuarioModel() throws Exception {
        connection = SqliteConnection.connect();
        if (connection == null) throw new Exception("No se pudo establecer conexión con la base de datos.");
    }

    public boolean isDBConnected() throws SQLException {
        return connection != null && !connection.isClosed();
    }
    
    public boolean existCuenta(String num_cuenta) throws SQLException {
        String query = "SELECT * FROM Cuentas WHERE num_cuenta = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num_cuenta);
        
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return true;
            else{
                return false;
            }
        } catch(Exception e){
            return false;
        } finally{
            preparedStatement.close();
            resultSet.close();
        }
    }
    
    public int getID_Usuario(String num_cuenta) throws SQLException {
        String query = "SELECT id FROM Usuarios WHERE num_cuenta = ? ";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, num_cuenta);
        
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    System.out.println("Usuario no encontrado.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    public String getNum_Cuenta(int id_usuario) throws SQLException {
        String query = "SELECT num_cuenta FROM Cuentas WHERE id_usuario = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_usuario);
        
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("num_cuenta");
                } else {
                    System.out.println("Cuenta no encontrada.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;   
    }
    
    public double getDinero(int id_usuario) throws SQLException {
        String query = "SELECT dinero FROM Cuentas WHERE id_usuario = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_usuario);
        
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("dinero");
                } else {
                    System.out.println("Cuenta no encontrada.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    public String getnombre(int id_usuario) throws SQLException {
        String query = "SELECT nombre FROM Cuentas WHERE id_usuario = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_usuario);
        
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nombre");
                } else {
                    System.out.println("Cuenta no encontrada.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;   
    }
    
    public void update_dinero(int id_usuario, double saldo) throws SQLException {
        String query = "UPDATE Cuentas SET dinero = ? WHERE id_usuario = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, saldo);
            preparedStatement.setInt(2, id_usuario);
        
            int filasAfectadas = preparedStatement.executeUpdate();
        
            if (filasAfectadas == 0) {
                System.out.println("Error al realizar update dinero ");
            } else {
                System.out.println("Update dinero realizado con exito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al realizar el update dinero: " + e.getMessage());
        }
    }
    
    public void eliminarUsuario(int id_usuario) {
        if (id_usuario == -1) { 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un usuario antes de intentar eliminarlo.");
            alert.showAndWait();
            return;
        }

        String queryCuentas = "DELETE FROM Cuentas WHERE id_usuario = ?";
        String queryUsuario = "DELETE FROM Usuarios WHERE id = ?";

        try (PreparedStatement preparedStatementCuenta = connection.prepareStatement(queryCuentas);
                PreparedStatement preparedStatementUsuario = connection.prepareStatement(queryUsuario)) {
            preparedStatementCuenta.setInt(1, id_usuario);

            int filasAfectadas = preparedStatementCuenta.executeUpdate();

            if (filasAfectadas == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mensaje del sistema");
                alert.setHeaderText(null);
                alert.setContentText("Error al eliminar el usuario");
                alert.showAndWait();
            } else {
                preparedStatementUsuario.setInt(1, id_usuario);
                preparedStatementUsuario.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensaje del sistema");
                alert.setHeaderText(null);
                alert.setContentText("Usuario eliminado con éxito.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
}
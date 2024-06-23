package cajero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioModel {
    private Connection connection;

    public UsuarioModel() throws Exception {
        connection = SqliteConnection.connect();
        if (connection == null) throw new Exception("No se pudo establecer conexión con la base de datos.");
    }

    public boolean isDBConnected() throws SQLException {
        return connection != null && !connection.isClosed();
    }
    
    public int getID_Usuario(String num_cuenta, String password) throws SQLException {
        String query = "SELECT id FROM Usuarios WHERE num_cuenta = ? AND password = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, num_cuenta);
            preparedStatement.setString(2, password);
        
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    System.out.println("Usuario no encontrado o contraseña incorrecta.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
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
}
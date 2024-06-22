package cajero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    private Connection connection;

    public LoginModel() throws Exception {
        connection = SqliteConnection.connect();
        if (connection == null) System.exit(1);
    }

    public boolean isDBConnected() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public boolean isLogin(String num_cuenta, String password) throws SQLException {
        String query = "SELECT * FROM Usuarios WHERE num_cuenta = ? AND password = ?";
        return executeQuery(query, num_cuenta, password);
    }

    public boolean isAdmin(String num_cuenta, String password) throws SQLException {
        String query = "SELECT * FROM Usuarios WHERE num_cuenta = ? AND password = ? AND admin = true";
        return executeQuery(query, num_cuenta, password);
    }

    private boolean executeQuery(String query, String num_cuenta, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, num_cuenta);
            preparedStatement.setString(2, password);
        
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
}
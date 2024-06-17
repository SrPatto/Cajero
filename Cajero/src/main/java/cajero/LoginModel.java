package cajero;

import java.sql.*;

public class LoginModel {
    Connection connection;
    public LoginModel(){
//        connection.SqliteConnection.Connector();
        if(connection == null) System.exit(1);
    }
    
    public boolean isDBConnected() throws SQLException{
        try{
            return !connection.isClosed();
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isLogin(String user, String pass) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        // base de datos
        String query = "SELECT * FROM users WHERE username = ? AND password = ? ";
        
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(0, user);
            preparedStatement.setString(0, pass);
            
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

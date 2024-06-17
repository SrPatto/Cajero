package cajero;

import java.sql.*;

public class SqliteConnection {
    public static Connection Connector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:NOMBREBD.sqlite");
            return con;
        } catch(Exception e){
            return null;
        }
    }
}

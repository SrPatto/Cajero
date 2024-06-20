package cajero;

import java.sql.Connection;
import java.sql.DriverManager;


public class SqliteConnection {
    public static Connection Connector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:NOMBREBD.sqlite");
            return con;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}

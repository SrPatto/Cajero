package cajero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqliteConnection {
    private static final String DB = "CajeroDB.db";

    public static Connection connect() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + DB);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void configuraDB() {
        String crearTablaUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (\n"
                + " id integer PRIMARY KEY,\n"
                + " num_cuenta text NOT NULL,\n"
                + " password text NOT NULL,\n"
                + " admin boolean NOT NULL DEFAULT 0\n"
                + ");";

        String crearTablaCuentas = "CREATE TABLE IF NOT EXISTS Cuentas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_cuenta TEXT NOT NULL UNIQUE," +
                "nombre TEXT NOT NULL," +
                "dinero REAL NOT NULL," +
                "id_usuario INTEGER," +
                "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)" + ");";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB);
             Statement stmt = conn.createStatement()) {
            stmt.execute(crearTablaUsuarios);
            stmt.execute(crearTablaCuentas);
            
            insertarDatosIni(conn);
            System.out.println("Tables have been created and initialized.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void insertarDatosIni(Connection conn) {
        String checkUsersQuery = "SELECT COUNT(*) AS count FROM Usuarios";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkUsersQuery)) {

            if (rs.next() && rs.getInt("count") == 0) {
                String insertarUsuarioQuery = "INSERT INTO Usuarios (num_cuenta, password, admin) VALUES (?, ?, ?)";
                String insertarCuentaQuery = "INSERT INTO Cuentas (num_cuenta, nombre, dinero, id_usuario) VALUES (?, ?, ?, ?)";
                
                try (PreparedStatement pstmtUsuario = conn.prepareStatement(insertarUsuarioQuery, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement pstmtCuenta = conn.prepareStatement(insertarCuentaQuery)) {

                    // Insertar Datos de admin
                    pstmtUsuario.setString(1, "7070707070");
                    pstmtUsuario.setString(2, "123");
                    pstmtUsuario.setBoolean(3, true);
                    pstmtUsuario.executeUpdate();
                    ResultSet generatedKeys = pstmtUsuario.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int adminId = generatedKeys.getInt(1);
                        System.out.println("Admin ID: " + adminId);

                        // Insertar datos de Cuenta Admin
                        pstmtCuenta.setString(1, "7070707070");
                        pstmtCuenta.setString(2, "Aurora Mendez Juarez");
                        pstmtCuenta.setDouble(3, 9006.0);
                        pstmtCuenta.setInt(4, adminId);
                        pstmtCuenta.executeUpdate();
                    }
                    
                    // Insertar Datos de usuario 1
                    pstmtUsuario.setString(1, "1010101010");
                    pstmtUsuario.setString(2, "1234");
                    pstmtUsuario.setBoolean(3, false);
                    pstmtUsuario.executeUpdate();
                    generatedKeys = pstmtUsuario.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int user1Id = generatedKeys.getInt(1);
                        System.out.println("User1 ID: " + user1Id);

                        // Insertar datos de Cuenta 1
                        pstmtCuenta.setString(1, "1010101010");
                        pstmtCuenta.setString(2, "Jose Perez Sanchez");
                        pstmtCuenta.setDouble(3, 200.0);
                        pstmtCuenta.setInt(4, user1Id);
                        pstmtCuenta.executeUpdate();
                    }

                    // Insertar Datos de usuario 2
                    pstmtUsuario.setString(1, "3030303030");
                    pstmtUsuario.setString(2, "12345");
                    pstmtUsuario.setBoolean(3, false);
                    pstmtUsuario.executeUpdate();
                    generatedKeys = pstmtUsuario.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int user2Id = generatedKeys.getInt(1);
                        System.out.println("User2 ID: " + user2Id);

                        // Insertar datos de Cuenta 2
                        pstmtCuenta.setString(1, "3030303030");
                        pstmtCuenta.setString(2, "Emilio Gomez Salazar");
                        pstmtCuenta.setDouble(3, 10000.0);
                        pstmtCuenta.setInt(4, user2Id);
                        pstmtCuenta.executeUpdate();
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

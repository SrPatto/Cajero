package cajero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class SqliteConnection {
    private static final String DB = "CajeroDB.db";

    static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + DB);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
   //Crear Tablas o base de datos solo si no existen
    public static void configuraDB() {
        String crearTablaUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_cuenta TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "admin TEXT NOT NULL CHECK (admin IN ('True', 'False'))" + ");";

        String crearTablaCuentas = "CREATE TABLE IF NOT EXISTS Cuentas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_cuenta TEXT NOT NULL UNIQUE," +
                "nombre TEXT NOT NULL," +
                "dinero REAL NOT NULL," +
                "id_usuario INTEGER," +
                "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)" + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(crearTablaUsuarios); //Ejecutan las sentencias sql para crear las tablas usuarios y cuentas.
            stmt.execute(crearTablaCuentas); //Statement

            // Inserta Datos por default si la tabla esta vacia
            insertarDatosIni(conn);

        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
            ex.printStackTrace();  // Imprime la traza de la excepción para más detalles
        }
    }

    private static void insertarDatosIni(Connection conn) { //Nos ayuda a insertar los datos iniciales
        String checkUsersQuery = "SELECT COUNT(*) AS count FROM Usuarios";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkUsersQuery)) { //Ejecuta la consulta para contar los registros en la tabla usuarios.

            if (rs.next() && rs.getInt("count") == 0) {
                // Insertar usuarios Iniciales
                String insertarUsuarioQuery = "INSERT INTO Usuarios (num_cuenta, password, admin) VALUES (?, ?, ?)";
                String insertarCuentaQuery = "INSERT INTO Cuentas (num_cuenta, nombre, dinero, id_usuario) VALUES (?, ?, ?, ?)";
                
                try (PreparedStatement pstmtUsuario = conn.prepareStatement(insertarUsuarioQuery, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement pstmtCuenta = conn.prepareStatement(insertarCuentaQuery)) {

                    // Insertar Datos de admin
                    pstmtUsuario.setString(1, "7070707070"); // Establece parametros en las sentencias preparadas (PreparedStatement) para insertar datos en la tabla uuarios.
                    pstmtUsuario.setString(2, "123");
                    pstmtUsuario.setString(3, "True");
                    pstmtUsuario.executeUpdate();
                    ResultSet generatedKeys = pstmtUsuario.getGeneratedKeys();
                    generatedKeys.next();
                    int adminId = generatedKeys.getInt(1); 

                    // Insrtar Datos de usuario 1
                    pstmtUsuario.setString(1, "1010101010");
                    pstmtUsuario.setString(2, "1234");
                    pstmtUsuario.setString(3, "False");
                    pstmtUsuario.executeUpdate();
                    generatedKeys = pstmtUsuario.getGeneratedKeys();
                    generatedKeys.next();
                    int user1Id = generatedKeys.getInt(1); //getInt obtiene el valor de la columna en la posición especificada en el resultado de una consulta.

                    // Insertar Datos de usuario 2
                    pstmtUsuario.setString(1, "3030303030");
                    pstmtUsuario.setString(2, "12345");
                    pstmtUsuario.setString(3, "False");
                    pstmtUsuario.executeUpdate();
                    generatedKeys = pstmtUsuario.getGeneratedKeys();
                    generatedKeys.next();
                    int user2Id = generatedKeys.getInt(1);

                    // Insertar datos de Cuenta 1
                    pstmtCuenta.setString(1, "1010101010");
                    pstmtCuenta.setString(2, "Jose Perez Sanchez");
                    pstmtCuenta.setDouble(3, 200.0);
                    pstmtCuenta.setInt(4, user1Id);
                    pstmtCuenta.executeUpdate();
                    
                    
                    // Insertar datos de Cuenta 2
                    pstmtCuenta.setString(1, "3030303030");
                    pstmtCuenta.setString(2, "Emilio Gomez Salazar");
                    pstmtCuenta.setDouble(3, 10000.0);
                    pstmtCuenta.setInt(4, user2Id);
                    pstmtCuenta.executeUpdate();

                    // Insertar datos de Cuenta Admin
                    pstmtCuenta.setString(1, "7070707010");
                    pstmtCuenta.setString(2, "Aurora Mendez Juarez");
                    pstmtCuenta.setDouble(3, 9006.0);
                    pstmtCuenta.setInt(4, adminId);
                    pstmtCuenta.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
            ex.printStackTrace();  // Imprime la traza de la excepción para más detalles
        }
    }
}

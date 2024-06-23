package cajero;

import java.sql.SQLException;

public class Cuenta {
    private int id_usuario;
    private String num_cuenta;
    private String nombre;
    private double dinero;
    
    public UsuarioModel usuarioModel;

    public Cuenta(int id_usuario) throws SQLException, Exception {
        this.id_usuario = id_usuario;
        this.usuarioModel = new UsuarioModel();
        
        setNum_cuenta(usuarioModel.getNum_Cuenta(id_usuario));
        setNombre(usuarioModel.getnombre(id_usuario));
        setDinero(usuarioModel.getDinero(id_usuario));
    }
    
    public Cuenta(String num_cuenta) throws SQLException, Exception {
        this.usuarioModel = new UsuarioModel();
        
        this.id_usuario = usuarioModel.getID_Usuario(num_cuenta);
        setNum_cuenta(num_cuenta);
        setNombre(nombre);
        setDinero(usuarioModel.getDinero(id_usuario));
    }
    
    public Cuenta(String nombre, String num_cuenta) throws SQLException, Exception {
        this.usuarioModel = new UsuarioModel();
        
        this.id_usuario = usuarioModel.getID_Usuario(num_cuenta);
        setNum_cuenta(num_cuenta);
        setNombre(nombre);
        setDinero(usuarioModel.getDinero(id_usuario));
    }

    // Métodos getters y setters
    public String getNum_cuenta() {
        return num_cuenta;
    }
    public void setNum_cuenta(String num_cuenta) {
        this.num_cuenta = num_cuenta;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}

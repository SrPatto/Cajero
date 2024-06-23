package cajero;

import java.sql.SQLException;

public class Cuenta {
    private int id_usuario;
    private String nombre;
    private double dinero;
    public UsuarioModel usuarioModel;

    public Cuenta(int id_usuario) throws SQLException, Exception {
        this.id_usuario = id_usuario;
        this.usuarioModel = new UsuarioModel();
        
        setNombre(usuarioModel.getnombre(id_usuario));
        setDinero(usuarioModel.getDinero(id_usuario));
    }

    // Métodos getters y setters
    public String getNombrePropietario() {
        return nombre;
    }

    public void setNombre(String nombrePropietario) {
        this.nombre = nombrePropietario;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }
}


package cajero;

public abstract class Usuario {
    protected String num_cuenta;
    protected String password;
    protected Cuenta cuenta;
    
//    public String getNum_cuenta() {
//        return num_cuenta;
//    }
//    public void setNum_cuenta(String num_cuenta) {
//        this.num_cuenta = num_cuenta;
//    }
//    
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Cuenta getCuenta() {
//        return cuenta;
//    }
//    public void setCuenta(Cuenta cuenta) {
//        this.cuenta = cuenta;
//    }
}

class Cliente extends Usuario {
    public Cliente(String num_Cuenta, String password, Cuenta cuenta) {
        this.num_cuenta = num_Cuenta;
        this.password = password;
        this.cuenta = cuenta;
    }

    public void depositar(double cantidad) {
        // Lógica para depositar dinero
    }

    public void retirar(double cantidad) {
        // Lógica para retirar dinero
    }

    public void transferir(double cantidad, Cuenta destino) {
        // Lógica para transferir dinero
    }
}

class Admin extends Usuario {
    public Admin(String num_Cuenta, String password, Cuenta cuenta) {
        this.num_cuenta = num_Cuenta;
        this.password = password;
        this.cuenta = cuenta;
    }

    public void agregarUsuario() {
        // Lógica para agregar un usuario
    }

    public void eliminarUsuario() {
        // Lógica para eliminar un usuario
    }

    public void actualizarUsuario() {
        // Lógica para actualizar un usuario
    }
}
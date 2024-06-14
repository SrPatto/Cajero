module proyectopoo.cajero {
    requires javafx.controls;
    requires javafx.fxml;


    opens proyectopoo.cajero to javafx.fxml;
    exports proyectopoo.cajero;
}
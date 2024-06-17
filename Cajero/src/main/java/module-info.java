module cajero {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens cajero to javafx.fxml;
    opens cajero.Usuario to javafx.fxml;
    exports cajero;
}

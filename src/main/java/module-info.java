module com.proyectofinalpoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.proyectofinalpoo.app to javafx.fxml;
    exports com.proyectofinalpoo.app;
}
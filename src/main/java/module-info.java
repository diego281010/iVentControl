module com.proyectofinalpoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.proyectofinalpoo.app to javafx.fxml;
    exports com.proyectofinalpoo.app;
}
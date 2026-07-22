package com.proyectofinalpoo.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/proyectofinalpoo/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/com/proyectofinalpoo/images/logo.png")));

        stage.setTitle("iVentControl - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
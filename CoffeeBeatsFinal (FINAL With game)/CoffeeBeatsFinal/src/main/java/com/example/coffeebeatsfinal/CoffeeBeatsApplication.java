package com.example.coffeebeatsfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CoffeeBeatsApplication extends Application {

    private static Stage window;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(CoffeeBeatsApplication.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 614);
        window.setTitle("Coffee Beats");
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }
    public void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        window.getScene().setRoot(fxmlLoader.load());
        window.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}
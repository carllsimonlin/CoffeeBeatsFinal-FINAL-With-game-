package com.example.coffeebeatsfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button Library;

    @FXML
    private Button MusicQueue;

    @FXML
    private Button Playlists;

    @FXML
    private Button Search;
    @FXML
    private Button game;

    @FXML
    void onMusicQueue(ActionEvent event) throws IOException{
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("MusicPlay.fxml");
    }

    @FXML
    void onLibrary(ActionEvent event) throws IOException {
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("Library.fxml");
    }

    @FXML
    void onPlaylists(ActionEvent event) throws IOException{
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("Playlists.fxml");
    }

    @FXML
    void onSearch(ActionEvent event) throws IOException{
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("Search.fxml");
    }

    @FXML
    void onGame(ActionEvent event) throws IOException{
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("MusicGame.fxml");
    }
}

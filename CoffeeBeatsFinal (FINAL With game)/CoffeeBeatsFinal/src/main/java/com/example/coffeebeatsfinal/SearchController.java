package com.example.coffeebeatsfinal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SearchController {

    @FXML
    private Button Home;

    @FXML
    private TextField field;

    @FXML
    private ImageView albumCover;

    @FXML
    private Button playBtn;

    @FXML
    private Button repeatBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label songDisplay;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ScrollPane displayInfo;

    @FXML
    private ListView<String> recentlyPlayedListView;

    private MediaPlayer player;

    private RecentlyPlayedManager recentlyPlayedManager = RecentlyPlayedManager.getInstance();

    private List<Song> songList = new LinkedList<>(); // Use a list to store songs

    public void initialize() {
        try {
            Scanner scanner = new Scanner(new FileReader("CoffeeBeatsFinal/Playlist/MyLibrary.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(",");
                Song song = new Song(split);
                songList.add(song); // Add song to the list
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Load recently played songs from the manager
        recentlyPlayedListView.setItems(recentlyPlayedManager.getRecentlyPlayedList());

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (player != null) {
                    player.setVolume(volumeSlider.getValue() * 0.01);
                }
            }
        });
    }

    @FXML
    void onHome(ActionEvent event) throws IOException {
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("Home.fxml");
        if (player != null) {
            player.dispose();
        }
    }

    private boolean running;

    @FXML
    void onSearch(ActionEvent event) {
        if (player != null) {
            player.stop();
        }

        String name = field.getText().toUpperCase();
        Song songs = searchSong(name);
        if (songs != null) {
            songDisplay.setText(songs.Nameout() + " - " + songs.Artout());
            albumCover.setImage(songs.Getcover());

            // Add the current song to the recently played list
            String songInfo = songs.Nameout() + " - " + songs.Artout();
            recentlyPlayedManager.addSong(songInfo);

            player = new MediaPlayer(songs.Songout());
            player.play();
            player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                double progress = player.getCurrentTime().toSeconds() / player.getTotalDuration().toSeconds();
                progressBar.setProgress(progress);
            });
            progressBar.setStyle("-fx-accent:  #411530;");
            running = true;

            displaySongInformation(name);
            // Clear the search field after displaying information
            field.clear();
        }
    }

    private Song searchSong(String name) {
        for (Song song : songList) {
            if (song.Nameout().equalsIgnoreCase(name)) {
                return song;
            }
        }
        return null;
    }

    private void displaySongInformation(String songTitle) {
        try {
            File file = new File("CoffeeBeatsFinal/Playlists/" + songTitle + ".txt");
            Scanner scanner = new Scanner(file);

            TextFlow textFlow = new TextFlow();
            textFlow.setPrefWidth(400);
            textFlow.setPrefHeight(250);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Text text = new Text(line + "\n");
                text.setStyle("-fx-font-family: 'Berlin Sans FB'; -fx-font-size: 16;");
                textFlow.getChildren().add(text);
            }

            textFlow.setStyle("-fx-text-alignment: justify;");

            // Clear existing children and add the TextFlow to the displayInfo ScrollPane
            displayInfo.setContent(textFlow);

        } catch (FileNotFoundException e) {
            // Handle the case when the information file is not found
            System.out.println("Information file not found for: " + songTitle);
        }
    }

    private boolean loop = false;

    @FXML
    public void onRepeat(ActionEvent event) {
        loop = !loop;
        if (player != null) {
            player.seek(Duration.ZERO);
        }
    }

    @FXML
    public void onPlay(ActionEvent event) {
        if (!running && player != null) {
            player.play();
            running = true;
        } else if (player != null) {
            player.pause();
            running = false;
        }
    }
}

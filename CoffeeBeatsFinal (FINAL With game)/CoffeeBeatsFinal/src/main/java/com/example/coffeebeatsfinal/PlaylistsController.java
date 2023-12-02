package com.example.coffeebeatsfinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class PlaylistsController {

    @FXML
    private Button Home;

    @FXML
    private ScrollPane PlaylistsLinkedDisplay;

    @FXML
    private Button backButton; // Added back button

    @FXML
    private ChoiceBox<String> libraryChoiceBox;

    @FXML
    private Button addToPlaylistButton;

    @FXML
    private Label playlistNameLabel; // Added Label for playlist name

    private LinkedList<Song> playlist = new LinkedList<>();
    private Map<String, ScrollPane> playlistsMap = DataHolder.getPlaylistsMap(); // Use the shared map
    private Node initialContent; // Added initialContent for storing the initial content

    @FXML
    void initialize() {
        populateLibraryChoiceBox();
        displayPlaylists();
        // Save the initial content of the PlaylistsLinkedDisplay
        initialContent = PlaylistsLinkedDisplay.getContent();
    }

    @FXML
    void onHome(ActionEvent event) throws IOException {
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("Home.fxml");
        // Do not reset or change the content here
    }

    @FXML
    void onBack(ActionEvent event) {
        // Handle going back to the main screen of Playlists.fxml
        PlaylistsLinkedDisplay.setContent(initialContent);
        playlistNameLabel.setText(""); // Clear playlist name when going back
    }

    private void populateLibraryChoiceBox() {
        ObservableList<String> songNames = FXCollections.observableArrayList();
        try {
            Scanner scanner = new Scanner(new java.io.File("CoffeeBeatsFinal/Playlist/MyLibrary.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(",");
                Song song = new Song(split);
                songNames.add(song.Nameout());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        libraryChoiceBox.setItems(songNames);
    }

    @FXML
    void onAddToPlaylist(ActionEvent event) {
        String selectedSongName = libraryChoiceBox.getValue();
        if (selectedSongName != null) {
            Song selectedSong = findSongByName(selectedSongName);
            if (selectedSong != null) {
                playlist.add(selectedSong);
                ScrollPane currentPlaylist = getCurrentPlaylist();
                displaySongInfo(selectedSong, currentPlaylist);
            }
        }
    }

    private Song findSongByName(String name) {
        try {
            Scanner scanner = new Scanner(new java.io.File("CoffeeBeatsFinal/Playlist/MyLibrary.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(",");
                Song song = new Song(split);
                if (song.Nameout().equals(name)) {
                    return song;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void displaySongInfo(Song song, ScrollPane currentPlaylist) {
        Label songInfoLabel = new Label(song.Nameout() + " - " + song.Artout());
        songInfoLabel.setStyle("-fx-font-family: 'Berlin Sans FB'; -fx-font-size: 20px; -fx-text-fill: #000000;-fx-font-weight: bold;");

        Node contentNode = currentPlaylist.getContent();

        if (contentNode instanceof AnchorPane) {
            AnchorPane contentPane = (AnchorPane) contentNode;
            double newY = contentPane.getChildren().size() * 35.0;
            songInfoLabel.setLayoutY(newY);
            contentPane.getChildren().add(songInfoLabel);
            contentPane.setMinHeight(newY + 40.0);
        }
    }

    @FXML
    void onCreatePlaylists(ActionEvent event) {
        createPlaylist();
    }

    private void createPlaylist() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText("Enter the name of the playlist:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(playlistName -> {
            if (!playlistsMap.containsKey(playlistName)) {
                ScrollPane playlistScrollPane = new ScrollPane();
                playlistScrollPane.setPrefSize(758, 454);
                playlistScrollPane.setStyle("-fx-background: #FFFFFF;");

                AnchorPane playlistContent = new AnchorPane();
                playlistScrollPane.setContent(playlistContent);

                playlistsMap.put(playlistName, playlistScrollPane);
                displayPlaylistButton(playlistName);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Playlist already exists!");
                alert.showAndWait();
            }
        });
    }

    private void displayPlaylistButton(String playlistName) {
        Button playlistButton = new Button(playlistName);
        playlistButton.setStyle("-fx-font-family: 'Berlin Sans FB'; -fx-font-size: 20px; -fx-text-fill: #000000; -fx-padding: 10px;");
        playlistButton.setMinWidth(750);

        double newY = ((AnchorPane) PlaylistsLinkedDisplay.getContent()).getChildren().size() * 60.0;

        playlistButton.setOnAction(event -> switchToPlaylist(playlistName));
        playlistButton.setLayoutY(newY);
        ((AnchorPane) PlaylistsLinkedDisplay.getContent()).getChildren().add(playlistButton);
    }

    private void switchToPlaylist(String playlistName) {
        ScrollPane selectedPlaylist = playlistsMap.get(playlistName);
        if (selectedPlaylist != null) {
            PlaylistsLinkedDisplay.setContent(selectedPlaylist);
            updatePlaylistNameLabel(playlistName);
        }
    }

    private void updatePlaylistNameLabel(String playlistName) {
        playlistNameLabel.setText(playlistName);
        playlistNameLabel.setStyle("-fx-font-family: 'Berlin Sans FB'; -fx-font-size: 30px; -fx-text-fill: #f5f5dc; -fx-font-weight: bold;");
    }

    private void displayPlaylists() {
        for (String playlistName : playlistsMap.keySet()) {
            displayPlaylistButton(playlistName);
        }
    }

    private ScrollPane getCurrentPlaylist() {
        Node contentNode = PlaylistsLinkedDisplay.getContent();
        if (contentNode instanceof ScrollPane) {
            return (ScrollPane) contentNode;
        }
        return null;
    }
}

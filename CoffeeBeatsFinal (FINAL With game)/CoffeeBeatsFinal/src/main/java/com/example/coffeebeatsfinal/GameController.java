package com.example.coffeebeatsfinal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {

    @FXML
    private Button Home;
    @FXML
    private Button guess;
    @FXML
    private Button nextBtn;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label songTxt;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ImageView albumCover;
    @FXML
    private TextField answer;
    private Queue<Song> songQueue;

    private boolean running; //playing or not
    private MediaPlayer mediaPlayer;
    Song current;
    int i;
    int guesses = 3;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        songQueue = new LinkedList<>();

        try {
            Scanner scanner = new Scanner(new FileReader("CoffeeBeatsFinal/Playlist/MyLibrary.txt"));

            i = 0;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] split = line.split(",");
                Song song = new Song(split);
                songQueue.offer(song);
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = new File("CoffeeBeatsFinal/Playlist/MyLibrary.txt");
        //System.out.println(System.getProperty("java.class.path"));


        if (!file.exists()) {
            // Handle the case where the Music directory is not found
            System.err.println("Music directory not found");
            return;
        }
        songTxt.setText(guesses+" Guesses Left");
        shuffle();


        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
    }

    @FXML
    public void nextBtnOnAction(ActionEvent event){
        mediaPlayer.stop();
        guesses = 3;

        current = songQueue.poll();

        if (current == null){
            mediaPlayer.dispose();
            guess.setDisable(true);
            songTxt.setText("You've gone through every song.");
        } else {
            songTxt.setText(guesses+" Guesses Left");
            albumCover.setImage(null);

            mediaPlayer = new MediaPlayer(current.Songout());
            mediaPlayer.play();

            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                double progress = mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
                progressBar.setProgress(progress);
            });
            progressBar.setStyle("-fx-accent:  #681330;");


        }

    }

    @FXML
    public void shuffle(){
        ArrayList<Song> songShuffle = new ArrayList<>(songQueue);

        Collections.shuffle(songShuffle);
        songQueue.clear();
        songQueue.addAll(songShuffle);

        current = songQueue.poll();

        mediaPlayer = new MediaPlayer(current.Songout());
        mediaPlayer.play();
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double progress = mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
            progressBar.setProgress(progress);
        });
        progressBar.setStyle("-fx-accent:  #681330;");


    }

    @FXML
    void onHome(ActionEvent event) throws IOException {
        mediaPlayer.dispose();
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("Home.fxml");

    }
    @FXML
    void onGuess(ActionEvent event) throws IOException {
        String ans = answer.getText().toUpperCase();
        if (ans.equals(current.Nameout())) {
            songTxt.setText(current.Nameout() + " by: " + current.Artout());
            albumCover.setImage(current.Getcover());
        } else if (guesses > 1) {
            guesses--;
            songTxt.setText(guesses + " Guesses Left");
        } else {
            songTxt.setText(current.Nameout() + " by: " + current.Artout());
            albumCover.setImage(current.Getcover());
        }
    }
}

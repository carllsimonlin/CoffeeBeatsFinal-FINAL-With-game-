package com.example.coffeebeatsfinal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.util.*;

public class MusicPlayController implements Initializable {

    @FXML
    private Button Home;

    @FXML
    private TextArea queueDisplay;

    @FXML
    private Button loopBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Button playBtn;


    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button shuffleBtn;

    @FXML
    private Label songTxt;

    @FXML
    private Slider volumeSlider;
    @FXML
    private ImageView albumCover;

    private Queue<Song> songQueue;

    private boolean running; //playing or not
    private boolean loop = false; //for song repeat

    private MediaPlayer mediaPlayer;
    Song current;
    int i;

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


        //to display in text Area
        for(Song song: songQueue){
            queueDisplay.appendText(song.Nameout() + " - " + song.Artout() + " - From: " + song.Albout() + "\n");
        }

        current = songQueue.poll();
        mediaPlayer = new MediaPlayer(current.Songout());

        songTxt.setText(current.Nameout() + " by: " + current.Artout());
        albumCover.setImage(current.Getcover());

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double progress = mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
            progressBar.setProgress(progress);
        });
        progressBar.setStyle("-fx-accent:  #681330;");


        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
    }

    @FXML
    public void playBtnOnAction(ActionEvent event){
        if(!running){
            mediaPlayer.play();
            running = true;
        } else {
            mediaPlayer.pause();
            running = false;
        }
    }

    @FXML
    public void nextBtnOnAction(ActionEvent event){
        mediaPlayer.stop();
        queueDisplay.setText("");
        for(Song song: songQueue){
            queueDisplay.appendText(song.Nameout() + " - " + song.Artout() + " - From: " + song.Albout() + "\n");
        }
        current = songQueue.poll();

        System.out.println(current.Nameout());

        if (current == null){
            mediaPlayer.dispose();
            songTxt.setText("No more song in queue.");
            queueDisplay.setText("");
        } else {

            System.out.println(mediaPlayer.getStatus());
            songTxt.setText(current.Nameout() + " by: " + current.Artout());
            albumCover.setImage(current.Getcover());

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
    public void shuffleBtnOnAction(ActionEvent event){
        mediaPlayer.stop();
        ArrayList<Song> songShuffle = new ArrayList<>(songQueue);

        Collections.shuffle(songShuffle);
        songQueue.clear();
        songQueue.addAll(songShuffle);

        Song newSong = songQueue.poll();
        queueDisplay.setText("");

        for(Song song: songQueue){
            queueDisplay.appendText(song.Nameout() + " - " + song.Artout() + " - From: " + song.Albout() + "\n");
        }

        mediaPlayer = new MediaPlayer(newSong.Songout());
        mediaPlayer.play();
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double progress = mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
            progressBar.setProgress(progress);
        });
        progressBar.setStyle("-fx-accent:  #681330;");


        songTxt.setText(newSong.Nameout() + " by: " + newSong.Artout());
        albumCover.setImage(newSong.Getcover());

    }

    @FXML
    public void repeatBtnOnAction(ActionEvent event){
        loop = !loop;
        mediaPlayer.seek(Duration.ZERO);
    }

    @FXML
    void onHome(ActionEvent event) throws IOException {
        CoffeeBeatsApplication application = new CoffeeBeatsApplication();
        application.changeScene("Home.fxml");
        mediaPlayer.dispose();
    }
}

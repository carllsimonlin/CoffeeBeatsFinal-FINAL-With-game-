package com.example.coffeebeatsfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class LibraryController {

    @FXML
    private Button Home;

    @FXML
    private ScrollPane MusicStackDisplay; //Compile a folder with mp3 music and just make program read the folder and display it here

    @FXML
    private TextArea displaySong;
    @FXML
    private Button sortAlbum;

    @FXML
    private Button sortName;
    LinkedList<Song> queue = new LinkedList<>();



    public void initialize(){
        Song song = new Song();
        try{
            File file = new File("CoffeeBeatsFinal/Playlist/MyLibrary.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                String temp = scanner.nextLine();

                String tempArr[] = temp.split(",");
                song = new Song(tempArr);
                queue.add(song);
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < queue.size(); i++){
            Song current = queue.get(i);
            sb.append(current.Nameout() + " | Artist: " + current.Artout() + " | Album: " + current.Albout() + "\n");
        }
        displaySong.setFont(Font.font("Berlin Sans FB", 20));
        displaySong.setText(sb.toString());
        displaySong.setEditable(false);

    }

    @FXML
    void onHome(ActionEvent event) throws IOException {
        com.example.coffeebeatsfinal.CoffeeBeatsApplication application = new com.example.coffeebeatsfinal.CoffeeBeatsApplication();
        application.changeScene("Home.fxml");
    }

    @FXML
    void onSongName(ActionEvent event){
        merg2 merge = new merg2();
        merge.mergeSort(queue);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < queue.size(); i++){
            Song current = queue.get(i);
            sb.append(current.Nameout() + " | Artist: " + current.Artout() + " | Album: " + current.Albout() + "\n");
        }
        displaySong.setFont(Font.font("Berlin Sans FB", 20));
        displaySong.setText(sb.toString());

    }

    @FXML
    void onAlbum(ActionEvent event){
        merg2 merge = new merg2();
        merge.mergeSortAlbum(queue);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < queue.size(); i++){
            Song current = queue.get(i);
            sb.append(current.Nameout() + " | Artist: " + current.Artout() + " | Album: " + current.Albout() + "\n");
        }

        displaySong.setFont(Font.font("Berlin Sans FB", 20));
        displaySong.setText(sb.toString());

    }

}

package com.example.coffeebeatsfinal;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import java.io.*;
import java.io.File;
import java.io.IOException;


public class Song {
    private File file;
    private Media media;
    private String Songname;
    private String ArtistName;
    private String AlbumName;
    private Image cover;
    private Image deft;
    Song(){

    }
    Song(String[] filearray) throws IOException {

        file = new File("CoffeeBeatsFinal/Music/"+ filearray[0].strip()+ ".mp3");
        Songname = file.getName().replace(".mp3","");
        ArtistName = filearray[1];
        AlbumName = filearray[2];
        media = new Media(file.toURI().toString());
        InputStream stream = new FileInputStream("CoffeeBeatsFinal/assets/" + filearray[2]+".png");
        cover = new Image(stream);

        stream = new FileInputStream("CoffeeBeatsFinal/assets/default.png");
        deft = new Image(stream);



    }

    public Media Songout(){
        return media;
    }


    public String Nameout(){
        return Songname;
    }
    public String Artout(){
        return ArtistName;
    }
    public String Albout(){
        return AlbumName;
    }

    public Image Getcover(){
        if (cover == null) return deft;
        return cover;
    }
}

package com.example.coffeebeatsfinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecentlyPlayedManager {
    private static RecentlyPlayedManager instance;
    private ObservableList<String> recentlyPlayedList;

    private RecentlyPlayedManager() {
        recentlyPlayedList = FXCollections.observableArrayList();
    }

    public static RecentlyPlayedManager getInstance() {
        if (instance == null) {
            instance = new RecentlyPlayedManager();
        }
        return instance;
    }

    public ObservableList<String> getRecentlyPlayedList() {
        return recentlyPlayedList;
    }

    public void addSong(String songInfo) {
        recentlyPlayedList.add(0, songInfo);
    }
}

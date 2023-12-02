package com.example.coffeebeatsfinal;

import javafx.scene.control.ScrollPane;

import java.util.HashMap;
import java.util.Map;

public class DataHolder {
    private static final Map<String, ScrollPane> playlistsMap = new HashMap<>();

    public static Map<String, ScrollPane> getPlaylistsMap() {
        return playlistsMap;
    }
}

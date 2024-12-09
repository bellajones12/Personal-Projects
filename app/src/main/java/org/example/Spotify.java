package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Spotify {
    public List<Song> songs;

    public Spotify() {
        songs = new ArrayList<Song>();
    }

    public void startApp() {
//        load data
        Path directoryPath = Paths.get("src/main/resources/Spotify Extended Streaming History/2024");
//        change to accept input based on required year and how many top songs wanted
        int year = 2024;
        int topSongsNumber = 10;
        try {
            // Using Files.walk() to traverse the directory
            Files.walk(directoryPath)
                    .filter(Files::isRegularFile) // Filter only files, not directories
                    .forEach(file -> {
                        new JsonManagement(file.toString(), songs, year); // Process each file
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

//        need error handling for if all songs are same, or are ties, or are only less than 5 songs listend to

        System.out.println("Top " + topSongsNumber + " Songs: ");
        List<Song> topSongs = new ArrayList<> ();

        songs.sort((s1, s2) -> Integer.compare(s2.totalListens, s1.totalListens));

        for (int i = 0; i < Math.min(topSongsNumber, songs.size()); i++) {
            Song song = songs.get(i);
            System.out.println((i+1) + ". " + song.title + ": " + song.totalListens + " plays");
        }

    }

}

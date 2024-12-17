package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Spotify {
    public List<Song> songs;
    public List<Song> topSongs;

    public Spotify() {
        songs = new ArrayList<Song>();
        topSongs = new ArrayList<Song>();
    }

    public void startApp() {
//        load data
        int year = 2024;
        Path directoryPath = Paths.get("src/main/resources/Spotify Extended Streaming History/2024");
//        change to accept input based on required year and how many top songs wanted
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

        songs.sort((s1, s2) -> Integer.compare(s2.totalListens, s1.totalListens));
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            topSongs.add(song);
        }
        UI ui = new UI(this);
        ui.displayMenu();

    }

    public void topSong(int topSongsNumber, String artist) {
        //        need error handling for if all songs are same, or are ties, or are only less than 5 songs listend to
        if (artist == null) {
            System.out.println("Top " + topSongsNumber + " Songs: ");

            for (int i = 0; i < Math.min(topSongsNumber, songs.size()); i++) {
                Song song = topSongs.get(i);
                System.out.println((i+1) + ". " + song.title + " by " + song.artist + ": " + song.totalListens + " plays");
            }
        }
        else {
            int counter = 0;
            int rank = 1;
            System.out.println("Top " + topSongsNumber + " Songs by: " + artist);
            for (Song song : topSongs) {
                rank ++;
//                    System.out.println(counter + ". " + songs.get(counter).artist + " -- " + artist);
                if (song.artist != null) {
                    if ((song.artist).equalsIgnoreCase(artist) && counter < topSongsNumber) {
                        System.out.println((counter+1) + ". " + song.title + ": " + song.totalListens + " plays with overall rank: " + rank);
                        counter ++;
                    }
                }
            }
        }
    }

    public void searchSong(String songTitle) {
        int totalPlays = 0;
        String artist = "";
        for (Song song : songs) {
            if (song.title != null) {
                if (songTitle.equals((song.title).toLowerCase())) {
                    totalPlays = song.totalListens;
                    artist = song.artist;
                    songTitle = song.title;
                }
            }
        }
//        add rank
        int rank = 0;
        int i = 1;
        for (Song song : topSongs) {
            if (song.title != null) {
                if (songTitle.equals((song.title))) {
                    rank = i;
                    break;
                }
            }
            i ++;
        }
        System.out.println(songTitle + ", " + artist + " has " + totalPlays + " plays with rank " + rank);
    }

}
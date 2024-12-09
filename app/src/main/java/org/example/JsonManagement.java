package org.example;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

public class JsonManagement {
    public String filename;
    private ObjectMapper mapper;
    List<Song> songs;
    int year;

    public JsonManagement(String filename, List<Song> songs, int year) {
        this.filename = filename;
        this.mapper = new ObjectMapper();
        this.songs = songs;
        this.year = year;
        loadData();
    }

    public void loadData() {
        try {
            // Deserialize into a List of Song objects
            List<Song> newSongs = mapper.readValue(new File(filename), new TypeReference<List<Song>>(){});

            for (Song newSong : newSongs) {
                int check = 0;
                for (Song song : songs) {
                    if (song.firstDate != null && dateCheck(song.firstDate) && lengthCheck(song.milliseconds)) {
                        if (song.title != null && song.title.equals(newSong.title)) {
                            song.newListen(newSong.firstDate);
                            check = 1;
                        }
                    }

                }
                if (check == 0 && dateCheck(newSong.firstDate) && lengthCheck(newSong.milliseconds)) {
                    songs.add(newSong);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean dateCheck(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        return year == this.year;
    }

    public boolean lengthCheck(int length) {
        return length >= 30000;
    }
}



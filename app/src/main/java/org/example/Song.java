package org.example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Song {
    @JsonProperty("master_metadata_track_name")
    public String title;
    @JsonProperty("master_metadata_album_artist_name")
    public String artist;
    @JsonProperty("ms_played")
    public int milliseconds;
    @JsonProperty("ts")
    @JsonDeserialize(using = CustomDateDeserialiser.class)
    public Date firstDate;
    public Date lastDate;
    public int totalListens;
    public Map<Date, Integer> listeningData;

        @JsonCreator
        public Song(@JsonProperty("master_metadata_track_name") String title, @JsonProperty("master_metadata_album_artist_name") String artist, @JsonProperty("ms_played") int milliseconds, @JsonProperty("ts") Date firstDate) {
            this.title = title;
        this.artist = artist;
        this.firstDate = firstDate;
        this.lastDate = firstDate;
        this.milliseconds = milliseconds;
        this.totalListens = 0;
        this.listeningData = new HashMap<>();
        this.newListen(firstDate);
    }

    public void newListen(Date currentDate) {
//        may need a check for how many milliseconds are listened to
//        current date will need to extract date from date timestamp to remove time played
        if (this.firstDate.compareTo(currentDate) > 0) {
            this.firstDate = currentDate;
        }
        if (this.lastDate.compareTo(currentDate) < 0) {
            this.lastDate = currentDate;
        }

        this.totalListens++;
        if (this.listeningData.containsKey(currentDate)) {
//            update listening total
            this.listeningData.compute(currentDate, (k, currentValue) -> currentValue + 1);
        } else {
            this.listeningData.put(currentDate, 1);
        }
    }
}

package org.example;

import java.util.Scanner;

public class UI {
    public Spotify spotify;

    public UI(Spotify spotify) {
        this.spotify = spotify;
    }

    public void displayMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(spotify.songs.size());
        while(true) {
            System.out.println("""
                    1. View Top Songs
                    2. Search Particular Song
                    3. Exit App
                    """);

            System.out.println("Enter your option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> {
                    int topSongs;
                    while (true) {
                        System.out.println("Enter the number of top songs, or enter '-1' to exit");
                        topSongs = scanner.nextInt();
                        if (topSongs == -1) {
                            break;
                        }
                        this.spotify.topSong(topSongs);
                    }
                }
                case "2" -> {
                    String songTitle;
                    while (true) {
                        System.out.println("Enter song title or enter 'exit' to exit");
                        songTitle = scanner.nextLine();
                        if (songTitle.equals("exit")) {
                            break;
                        }
                        this.spotify.searchSong(songTitle.toLowerCase());
                    }
                }
                case "3" -> {
                    return;
                }
                default -> System.out.println("Invalid Input");
            }

            // Wait for user to press "Enter" before displaying the menu again
            System.out.println("Press Enter to continue...");
            scanner.nextLine();  // Clear the newline left over from `scanner.next()`
            scanner.nextLine();  // Wait for the user to press Enter

            System.out.println("\n--------------------------------\n");
        }
    }
}

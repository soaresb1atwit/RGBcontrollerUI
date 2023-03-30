package com.example.rgbcontrollerui.UI;

import android.net.Uri;

public class Song {
    //members
    String title;
    Uri uri;
    Uri artworkUri;
    int size;
    String duration;

    //constructor
    public Song(String title, Uri uri, Uri artworkUri, int size, String duration) {
        this.title = title;
        this.uri = uri;
        this.artworkUri = artworkUri;
        this.size = size;
        this.duration = duration;
    }

    //getters

    public String getTitle() {
        return title;
    }

    public Uri getUri() {
        return uri;
    }

    public Uri getArtworkUri() {
        return artworkUri;
    }

    public int getSize() {

        return size;
    }

    public String getDuration() {
        if(String.valueOf(duration) != null) {
            Long time = Long.valueOf(duration);
            long seconds = time/1000;
            long minutes = seconds/60;
            seconds = seconds % 60;

            if(seconds<10) {
                duration = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
            } else {
                duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
            }
        } else {
            String nothing = "0";
        }
        return duration;
    }
}

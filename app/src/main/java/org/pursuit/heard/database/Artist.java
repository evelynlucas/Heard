package org.pursuit.heard.database;

import java.io.Serializable;

public class Artist implements Serializable {

    private String artist;
    private long id;

    public Artist(String artist, long id) {
        this.artist = artist;
        this.id = id;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }
}

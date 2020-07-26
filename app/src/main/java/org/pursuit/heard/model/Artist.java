package org.pursuit.heard.model;

import java.io.Serializable;

public class Artist implements Serializable  {

    private String artistName;
    private String artworkUrl100;

    public Artist(String artistName, String artworkUrl100) {
        this.artistName = artistName;
        this.artworkUrl100 = artworkUrl100;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

}

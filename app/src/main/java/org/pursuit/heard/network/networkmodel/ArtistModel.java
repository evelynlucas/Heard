package org.pursuit.heard.network.networkmodel;

import java.io.Serializable;

public class ArtistModel implements Serializable  {

    private String artistName;
    private String artworkUrl100;

    public ArtistModel(String artistName, String artworkUrl100) {
        this.artistName = artistName;
        this.artworkUrl100 = artworkUrl100;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }
}

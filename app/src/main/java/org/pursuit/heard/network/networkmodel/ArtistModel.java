package org.pursuit.heard.network.networkmodel;

public class ArtistModel {

    private String artistName;
    private String artworkUrl100;

    public ArtistModel(String artistName, String artworkUrl100) {
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

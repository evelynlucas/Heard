package org.pursuit.heard.database;

import org.pursuit.heard.network.networkmodel.ArtistModel;

import java.io.Serializable;
import java.util.List;

public class UserProfile {

    private String username;

    public String getUsername() {
        return username;
    }

    private List<String> artists;

    public List<String> getArtists() {
        return artists;
    }

    public UserProfile(String username, List<String> artists) {
        this.username = username;
        this.artists = artists;
    }

    public UserProfile(String username, List<String> artists, List<ArtistModel> artistList) {
        this.username = username;
        this.artists = artists;
        this.artistList = artistList;
    }

    private List<ArtistModel> artistList;

    public List<ArtistModel> getArtistList() {
        return artistList;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void setArtistList(List<ArtistModel> artistList) {
        this.artistList = artistList;
    }
}
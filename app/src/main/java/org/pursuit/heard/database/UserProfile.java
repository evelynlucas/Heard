package org.pursuit.heard.database;

import org.pursuit.heard.network.networkmodel.ArtistModel;

import java.io.Serializable;
import java.util.List;

public class UserProfile {

    private String username;
    private long id;
    private List<ArtistModel> artists;

    public UserProfile(String username, long id, List<ArtistModel> artists) {
        this.username = username;
        this.id = id;
        this.artists = artists;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setArtists(List<ArtistModel> artists) {
        this.artists = artists;
    }
}

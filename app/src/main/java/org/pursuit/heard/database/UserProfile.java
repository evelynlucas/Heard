package org.pursuit.heard.database;

import java.io.Serializable;
import java.util.List;

public class UserProfile{

    private String username;
    private long id;

    public String getUsername() {
        return username;
    }

    private List<String> artists;

    public List<String> getArtists() {
        return artists;
    }

    public UserProfile(String username, List<String> artists, long id) {
        this.username = username;
        this.artists = artists;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }
}

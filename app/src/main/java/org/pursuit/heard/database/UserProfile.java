package org.pursuit.heard.database;

import java.io.Serializable;
import java.util.List;

public class UserProfile{

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
}

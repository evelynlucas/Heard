package org.pursuit.heard.database;

import org.pursuit.heard.model.Artist;

import java.util.List;

public class UserProfile {

    private String username;
    private long id;
    private List<Artist> artists;

    public UserProfile(String username, long id, List<Artist> artists) {
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

    public List<Artist> getArtists() {
        return artists;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}

package org.pursuit.heard.model;

import java.util.List;

public class User {

    private String userName;
    private List<Artist> likedArtists;

    public User(String userName, List<Artist> likedArtists) {
        this.userName = userName;
        this.likedArtists = likedArtists;
    }

    public String getUserName() {
        return userName;
    }

    public List<Artist> getLikedArtists() {
        return likedArtists;
    }
}

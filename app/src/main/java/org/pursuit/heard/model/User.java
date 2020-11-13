package org.pursuit.heard.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String user_name;
    private List<String> followed_artists;

    public User() { }

    public User(String user_name, List<String> followed_artists) {
        this.user_name = user_name;
        this.followed_artists = followed_artists;
    }

    public String getUser_name() {
        return user_name;
    }

    public List<String> getFollowed_artists() {
        return followed_artists;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setFollowed_artists(List<String> followed_artists) {
        this.followed_artists = followed_artists;
    }
}

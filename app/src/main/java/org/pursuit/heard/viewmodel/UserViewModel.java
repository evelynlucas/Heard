package org.pursuit.heard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.model.Artist;

import java.io.Serializable;
import java.util.List;

public class UserViewModel extends AndroidViewModel implements Serializable {

    private String currentUser;
    private MutableLiveData<List<Artist>> likedArtists = new MutableLiveData<>();
    private ProfileDatabase database;

    public UserViewModel(@NonNull Application application, ProfileDatabase database) {
        super(application);
        this.database = database;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Long getUserID(String currentUser) {
        return database.getProfile(currentUser);
    }

    public MutableLiveData<List<Artist>> getLikedArtists() {
        long id = database.getProfile(currentUser);
        likedArtists.setValue(database.getArtists(id));
        return likedArtists;
    }

    public void setLikedArtists(MutableLiveData<List<Artist>> likedArtists) {
        this.likedArtists = likedArtists;
    }


}

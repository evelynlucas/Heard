package org.pursuit.heard.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.pursuit.heard.database.FirebaseRepository;
import org.pursuit.heard.model.Artist;
import org.pursuit.heard.model.User;

import java.io.Serializable;
import java.util.List;

public class MainUserViewModel extends ViewModel implements Serializable {

    private FirebaseRepository database = FirebaseRepository.getInstance();
    private User user;
    private String username;
    private MutableLiveData<List<Artist>> followedArtists = new MutableLiveData<>();

    public boolean setNewUser(String email, String password, String username) {
        database.createUser(email, password, username);
        if (database.isLoginSuccessful()) {
            user = database.getUserData();
            setUsername(user.getUser_name());
            return true;
        }
        return false;
    }

    public boolean verifyLogin(String email, String password) {
        database.verifyLogin(email, password);
        if (database.isLoginSuccessful()) {
            user = database.getUserData();
            setUsername(user.getUser_name());
            String e = database.getCurrentUser().getEmail();
            Log.d("EMAIL", "firebase " + e);
            return true;
        }
        return false;
    }

    public void addArtistForUser(Artist artist){
        database.updateFollowedArtists(artist);
    }

    public void fetchUserArtists() {
        database.fetchFollowedArtists(new FetchArtistListener() {
            @Override
            public void onArtistReceived(List<Artist> results) {
                followedArtists.setValue(results);
                MainUserViewModel.this.setFollowedArtists(followedArtists);
            }
        });
    }

    public void setFollowedArtists(MutableLiveData<List<Artist>> followedArtists) {
        this.followedArtists = followedArtists;
    }

    public MutableLiveData<List<Artist>> getFollowedArtists() {
        return followedArtists;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


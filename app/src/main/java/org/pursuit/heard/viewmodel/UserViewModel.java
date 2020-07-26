package org.pursuit.heard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.model.Artist;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<String> currentUser;
    private MutableLiveData<List<Artist>> likedArtists;
    private ProfileDatabase database;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<List<Artist>> getLikedArtists() {
        return likedArtists;
    }
}

package org.pursuit.heard.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.pursuit.heard.database.FirebaseRepository;
import org.pursuit.heard.model.Artist;

import java.io.Serializable;
import java.util.List;

public class UserViewModel extends ViewModel implements Serializable {

    private FirebaseRepository database = new FirebaseRepository();

    private String userName;
    private String userId;

    private MutableLiveData<List<Artist>> followedArtists = new MutableLiveData<>();

    public Boolean verifyLogin(String email, String password) {
        database.verifyLogin(email, password);
        if (database.isLoginSuccessful()) {
            String tempUsername = email.split("@")[0];
            setUserName(tempUsername.substring(0, 1).toUpperCase() + tempUsername.substring(1));
            setUserId();
            return true;
        }
        return false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId() {
        this.userId = database.getCurrentUser().getUid();;
    }

    public String getUserId() {
        return userId;
    }

    public void addArtistForUser(Artist artist){
        database.updateFollowedArtists(artist);
    }

    public void fetchMyArtists() {

    }


    //
//    private String currentUser;
//    private MutableLiveData<List<Artist>> likedArtists = new MutableLiveData<>();
//    private ProfileDatabase database;
//
//    public UserViewModel(ProfileDatabase database) {
//        this.database = database;
//    }
//
//    public String getCurrentUser() {
//        return currentUser;
//    }
//
//    public void setCurrentUser(String currentUser) {
//        this.currentUser = currentUser;
//    }
//
//    public Long getUserID(String currentUser) {
//        return database.getProfile(currentUser);
//    }
//
//    public MutableLiveData<List<Artist>> getLikedArtists() {
//        long id = database.getProfile(currentUser);
//        likedArtists.setValue(database.getArtists(id));
//        return likedArtists;
//    }
//
//    public void setLikedArtists(MutableLiveData<List<Artist>> likedArtists) {
//        this.likedArtists = likedArtists;
//    }
//

}

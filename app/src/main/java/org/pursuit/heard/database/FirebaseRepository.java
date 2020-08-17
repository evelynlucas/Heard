package org.pursuit.heard.database;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.pursuit.heard.model.Artist;
import org.pursuit.heard.utils.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FirebaseRepository {

    private final FirebaseAuth authorization = FirebaseAuth.getInstance();
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private final CollectionReference profiles = database.collection(Constants.PROFILES);
    private final CollectionReference artists = database.collection(Constants.ARTISTS);

    private FirebaseUser currentUser;
    private boolean loginSuccessful;

    public void verifyLogin(String email, String password) {
        authorization
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if ((task.isSuccessful())) {
                        this.currentUser = authorization.getCurrentUser();
                        setLoginSuccessful(true);
                    } else setLoginSuccessful(false);
                });
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public FirebaseAuth getAuthorization() {
        return authorization;
    }

    public FirebaseFirestore getDatabase() {
        return database;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void updateFollowedArtists(Artist artist) {
        profiles.whereEqualTo(Constants.USER_ID, currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    // Only if there exists a user
                    if (task.getResult() != null) { // if user does not exist
                        String userDoc = task.getResult()
                                .getDocuments()
                                .get(0)
                                .getId();

                        profiles.document(userDoc)
                                .update(Constants.FOLLOWED_ARTISTS,
                                        FieldValue.arrayUnion(artist.getArtistName()));
                    }
                });

        artists.whereEqualTo(Constants.ARTIST_NAME, artist.getArtistName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot snapshot = task.getResult();
                       if (!snapshot.getDocuments().isEmpty()) {
                           String artistDoc = snapshot
                                   .getDocuments()
                                   .get(0)
                                   .getId();

                           artists.document(artistDoc)
                                   .update(Constants.ARTIST_FOLLOWERS,
                                           FieldValue.arrayUnion(currentUser.getUid()));
                       } else {
                           Map<String, Object> docData = new HashMap<>();
                           docData.put(Constants.ARTIST_NAME, artist.getArtistName());
                           docData.put(Constants.ARTIST_IMAGE, artist.getArtworkUrl100());
                           docData.put(Constants.ARTIST_FOLLOWERS, FieldValue.arrayUnion(currentUser.getUid()));
                           artists.document().set(docData);

                       }
                    }
                });
    }
}

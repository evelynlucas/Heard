package org.pursuit.heard.database;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.pursuit.heard.model.Artist;
import org.pursuit.heard.utils.C;

import java.util.HashMap;
import java.util.Map;

public class FirebaseRepository {

    private final FirebaseAuth authorization = FirebaseAuth.getInstance();
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private final CollectionReference profiles = database.collection(C.PROFILES);
    private final CollectionReference artists = database.collection(C.ARTISTS);

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
        profiles.whereEqualTo(C.USER_ID, currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    // Only if there exists a user
                    if (task.getResult() != null) { // if user does not exist
                        String userDoc = task.getResult()
                                .getDocuments()
                                .get(0)
                                .getId();

                        profiles.document(userDoc)
                                .update(C.FOLLOWED_ARTISTS,
                                        FieldValue.arrayUnion(artist.getArtistName()));
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);

        artists.whereEqualTo(C.ARTIST_NAME, artist.getArtistName())
                .get()
                .addOnCompleteListener(task -> {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        if (!snapshot.getDocuments().isEmpty()) {
                            String artistDoc = snapshot
                                    .getDocuments()
                                    .get(0)
                                    .getId();

                            artists.document(artistDoc)
                                    .update(C.ARTIST_FOLLOWERS,
                                            FieldValue.arrayUnion(currentUser.getUid()));
                        } else {
                            Map<String, Object> docData = new HashMap<>();
                            docData.put(C.ARTIST_NAME, artist.getArtistName());
                            docData.put(C.ARTIST_IMAGE, artist.getArtworkUrl100());
                            docData.put(C.ARTIST_FOLLOWERS, FieldValue.arrayUnion(currentUser.getUid()));
                            artists.document().set(docData);

                        }
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);
    }
}

package org.pursuit.heard.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseRepository {


    private final FirebaseAuth authorization = FirebaseAuth.getInstance();
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private final CollectionReference profiles = database.collection("profiles");

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

    public void updateLikedArtists(String name) {
   //     CollectionReference profiles = database.collection("profiles");

        profiles.whereEqualTo("userID", currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.getResult() != null) {
                        String userDoc = task.getResult()
                                .getDocuments()
                                .get(0)
                                .getId();

                        profiles.document(userDoc)
                                .update("followedArtists", FieldValue.arrayUnion(name));
                    }
                });

    }
}

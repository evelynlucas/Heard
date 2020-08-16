package org.pursuit.heard.database;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class FirebaseRepository {

    private FirebaseAuth authorization = FirebaseAuth.getInstance();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
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

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser = currentUser;
    }
}

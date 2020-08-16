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

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void verifyLogin(String email, String password) {
        AtomicBoolean loginSuccessful = new AtomicBoolean(false);
        authorization
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if ((task.isSuccessful())) {
                        this.currentUser = authorization.getCurrentUser();
                        Log.d("FIREBASE", "login success");
                        setLoginSuccessful(true);
                    } else {
                        Log.d("FIREBASE", "login failure");
                        setLoginSuccessful(false);
                    }
                });
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

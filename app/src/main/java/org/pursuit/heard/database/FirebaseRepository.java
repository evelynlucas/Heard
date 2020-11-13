package org.pursuit.heard.database;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.oakwoodsc.rxfirestore.RxFirestoreDb;

import org.pursuit.heard.model.Artist;
import org.pursuit.heard.model.User;
import org.pursuit.heard.utils.C;
import org.pursuit.heard.viewmodel.FetchArtistListener;
import org.pursuit.heard.viewmodel.FetchMatchesListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirestore;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FirebaseRepository {

    private static FirebaseRepository INSTANCE = null;
    private FirebaseRepository() {}
    public static FirebaseRepository getInstance() {
        if (INSTANCE == null) {
            return new FirebaseRepository();
        } else throw new UnsupportedOperationException("Too many firebase instances");
    }

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private final CollectionReference profiles = database.collection(C.PROFILES);
    private final CollectionReference artists = database.collection(C.ARTISTS);

    private static FirebaseUser currentUser;
    private static User userData;
    private DocumentReference currentUserDocRef;
    private boolean loginSuccessful;

    @SuppressLint("CheckResult")
    public void createUser(String email, String password, String username) {
        RxFirebaseAuth
                .createUserWithEmailAndPassword(auth, email, password)
                .subscribeOn(Schedulers.io())
                .map(AuthResult::getUser)
                .subscribe(s -> {
                    createUserDoc(username, email);
                }, Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    private void createUserDoc(String username, String email) {
        Map<String, String> update = new HashMap<>();
        update.put(C.USER_NAME, username);

        RxFirestoreDb.set(profiles.document(email), update)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    setCurrentUserDocRef(profiles.document(email));
                    setCurrentUser(auth.getCurrentUser());
                    createUserData();
                })
                .subscribe(() -> setLoginSuccessful(true),
                        Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    public void verifyLogin(String email, String password) {
        RxFirebaseAuth
                .signInWithEmailAndPassword(auth, email, password)
                .subscribeOn(Schedulers.io())
                .map(authResult -> authResult.getUser() != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logged -> {
                    setCurrentUserDocRef(profiles.document(email));
                    setCurrentUser(auth.getCurrentUser());
                    createUserData();
                    setLoginSuccessful(logged);
                }, Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    private void createUserData() {
        if (currentUser.getEmail() != null) {
            RxFirestore.getDocument(profiles.document(currentUser.getEmail()), User.class)
                    .subscribe(this::setUserData, Throwable::printStackTrace);
        }
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public FirebaseFirestore getDatabase() {
        return database;
    }

    public DocumentReference getCurrentUserDocRef() {
        return currentUserDocRef;
    }

    public void setCurrentUserDocRef(DocumentReference currentUserDocRef) {
        this.currentUserDocRef = currentUserDocRef;
    }

    public FirebaseUser getCurrentUser() {
        Log.d("DATABASE", "getCurrentUser " + currentUser.getEmail());
        return currentUser;
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        FirebaseRepository.currentUser = currentUser;
    }

    public User getUserData() {
        Log.d("DATABASE", "getUserData" + userData.getUser_name());
        return userData;
    }

    public void setUserData(User userData) {
        FirebaseRepository.userData = userData;
    }

    public void updateFollowedArtists(Artist artist) {
        Map<String, Object> update = new HashMap<>();
        update.put(C.FOLLOWED_ARTISTS, FieldValue.arrayUnion(artist.getArtistName()));

        if (currentUser.getEmail() != null) {
            RxFirestoreDb.update(profiles.document(currentUser.getEmail()), update)
                    .subscribeOn(Schedulers.io())
                    .doOnTerminate(() -> updateArtistFollowers(artist))
                    .doOnError(Throwable::printStackTrace)
                    .subscribe();
        }
    }

    @SuppressLint("CheckResult")
    private void updateArtistFollowers(Artist artist) {
        Map<String, Object> update = new HashMap<>();
        update.put(C.ARTIST_NAME, artist.getArtistName());
        update.put(C.ARTIST_IMAGE, artist.getArtworkUrl100());
        update.put(C.ARTIST_FOLLOWERS, FieldValue.arrayUnion(currentUser.getEmail()));

        RxFirestoreDb.setAndMerge(artists.document(artist.getArtistName()), update)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    @SuppressLint("CheckResult")
    public void fetchFollowedArtists(final FetchArtistListener listener) {
        if (currentUser.getEmail() != null) {
            Query query = artists.whereArrayContains(C.ARTIST_FOLLOWERS, currentUser.getEmail());
            RxFirestoreDb.querySnapshots(query)
                    .subscribeOn(Schedulers.io())
                    .map(QuerySnapshot::getDocuments)
                    .map(d -> {
                        List<Artist> result = new ArrayList<>();
                        for (DocumentSnapshot ds: d) {
                            result.add(new Artist(
                                    (String) ds.get(C.ARTIST_NAME),
                                    (String) ds.get(C.ARTIST_IMAGE)));
                        }
                        return result;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(listener::onArtistReceived, Throwable::printStackTrace);
        }
    }

    @SuppressLint("CheckResult")
    public void fetchUserMatches(final FetchMatchesListener listener) {
        if (currentUser.getEmail() != null) {
            Query artists = this.artists.whereArrayContains(C.ARTIST_FOLLOWERS, currentUser.getEmail());
            RxFirestoreDb.querySnapshots(artists)
                    .subscribeOn(Schedulers.io())
                    .map(QuerySnapshot::getDocuments)
                    .map(d -> {
                        List<Object> followerEmails = new ArrayList<>();
                        for (DocumentSnapshot ds: d) {
                            followerEmails.addAll(Collections.singleton(ds.get(C.ARTIST_FOLLOWERS)));
                        }
                        return followerEmails;
                    })
                    .filter(f -> !f.contains(currentUser.getEmail()))
                    .map(f -> {
                        List<User> matchedUsers = new ArrayList<>();
                        for (Object e : f) {
                            String email = (String) e;
                            Query profiles = this.profiles.whereEqualTo(FieldPath.documentId(), email);
                            RxFirestoreDb.querySnapshots(profiles)
                                    .map(QuerySnapshot::getDocuments)
                                    .map(u -> matchedUsers.add(new User(
                                            (String) u.get(0).get(C.USER_NAME),
                                            (List<String>) u.get(0).get(C.FOLLOWED_ARTISTS)))
                                    )
                                    .doOnError(Throwable::printStackTrace)
                                    .subscribe();
                        }
                        return matchedUsers;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(listener::getMatches, Throwable::printStackTrace);

        } else throw new IllegalArgumentException("user email is null");
    }

}

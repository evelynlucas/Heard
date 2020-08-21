package org.pursuit.heard.database;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.oakwoodsc.rxfirestore.RxFirestoreDb;

import org.pursuit.heard.model.Artist;
import org.pursuit.heard.utils.C;

import java.util.HashMap;
import java.util.Map;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirestore;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FirebaseRepository {

    private final FirebaseAuth authorization = FirebaseAuth.getInstance();
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    private final CollectionReference profiles = database.collection(C.PROFILES);
    private final CollectionReference artists = database.collection(C.ARTISTS);

    private FirebaseUser currentUser;
    private DocumentReference userDocRef;
    private boolean loginSuccessful;

    @SuppressLint("CheckResult")
    public void createUser(String email, String password, String username) {
        RxFirebaseAuth
                .createUserWithEmailAndPassword(authorization, email, password)
                .subscribeOn(Schedulers.io())
                .map(AuthResult::getUser)
                .subscribe(s -> {
                    Log.d("AUTH", "reached here: " + username);
                    setDoc(username);
                }, Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    private void setDoc(String username) {
        Map<String, String> update = new HashMap<>();
        update.put("username", username);
        RxFirestoreDb.set(profiles.document(), update)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> setLoginSuccessful(true),
                        Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    public void verifyLogin(String email, String password) {
        RxFirebaseAuth
                .signInWithEmailAndPassword(authorization, email, password)
                .subscribeOn(Schedulers.io())
                .map(authResult -> authResult.getUser() != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logged -> {
                    this.currentUser = authorization.getCurrentUser();
                    setLoginSuccessful(true);
                }, Throwable::printStackTrace);
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

    public void fetchFollowedArtists() {
        Query query = profiles.whereEqualTo(C.USER_ID, currentUser.getUid());
        //     RxFirebaseDatabase.observeSingleValueEvent(query, Artist.class)

    }

    public void updateFollowedArtists(Artist artist) {
        updateArtistForUser(artist.getArtistName());
        updateArtistFollowers(artist);
    }

    @SuppressLint("CheckResult")
    private void updateArtistForUser(String artistName) {
        Map<String, Object> update = new HashMap<>();
        update.put(C.FOLLOWED_ARTISTS, FieldValue.arrayUnion(artistName));

        Query query = profiles.whereEqualTo(C.USER_ID, currentUser.getUid());
        RxFirestoreDb.querySnapshots(query)
                .subscribeOn(Schedulers.io())
                .map(QuerySnapshot::getDocuments)
                .takeWhile(ds -> !ds.isEmpty())
                .map(ds -> ds.get(0))
                .subscribe(d -> {
                    RxFirestoreDb
                            .update(d.getReference(), update)
                            .subscribe();
                }, Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    private void updateArtistFollowers(Artist artist) {
        Map<String, Object> update = new HashMap<>();
        update.put(C.ARTIST_NAME, artist.getArtistName());
        update.put(C.ARTIST_IMAGE, artist.getArtworkUrl100());
        update.put(C.ARTIST_FOLLOWERS, FieldValue.arrayUnion(currentUser.getUid()));

        Query query = artists.whereEqualTo(C.ARTIST_NAME, artist.getArtistName());
        RxFirestoreDb.querySnapshots(query)
                .subscribeOn(Schedulers.io())
                .map(QuerySnapshot::getDocuments)
                .subscribe(ds -> {
                    if (!ds.isEmpty()) {
                        RxFirestoreDb
                                .setAndMerge(ds.get(0).getReference(), update)
                                .subscribe();
                    } else {
                        RxFirestoreDb
                                .set(artists.document(), update)
                                .subscribe();
                    }
                }, Throwable::printStackTrace);

    }

//    public Observable<List<Artist>> getFollowedArtists() {
//
//
//    }


}

package org.pursuit.heard.network;

import android.annotation.SuppressLint;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArtistSearchManager {

    @SuppressLint("CheckResult")
    public void networkCall(final String artist, final NetworkCallback callback) {
        MusicRetrofit.getInstance()
                .create(APIService.class)
                .getArtist(artist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(resultsBase -> resultsBase.getResults().get(0))
                .subscribe(callback::onArtistReceived, Throwable::printStackTrace);
    }

}

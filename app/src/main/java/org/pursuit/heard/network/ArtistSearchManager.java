package org.pursuit.heard.network;

import android.annotation.SuppressLint;

import org.pursuit.heard.model.Artist;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
                .subscribe(new Consumer<Artist>() {
                    @Override
                    public void accept(Artist model) throws Exception {
                        callback.onArtistReceived(model);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }


//    public void networkCall(final String artist, final NetworkCallback callback) {
//        RetrofitSingleton
//                .getInstance()
//                .create(APIService.class)
//                .getArtist(artist)
//                .enqueue(new Callback<ResultsBase>() {
//                    @Override
//                    public void onResponse(Call<ResultsBase> call, Response<ResultsBase> response) {
//                        ResultsBase resultsBase = response.body();
//                     //   List<Artist> artistList = resultsBase.getResults();
//                        assert resultsBase != null;
//                        Artist result = resultsBase.getResults().get(0);
//                        Log.d(TAG, "onSuccess: " +  result.getArtistName());
//                  //      callback.onArtistReceived(artistList);
//                        callback.onArtistReceived(result);
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResultsBase> call, Throwable t) {
//                        Log.d(TAG, "onFailure: " + t.getMessage());
//                    }
//                });
//    }
}

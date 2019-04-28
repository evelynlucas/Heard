package org.pursuit.heard.network;

import android.util.Log;

import org.pursuit.heard.network.networkmodel.ArtistModel;
import org.pursuit.heard.network.networkmodel.ResultsBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistPresenter {

    private static final String TAG = "EvelynActivity";

    public void networkCall(String artist, final NetworkCallback callback) {
        RetrofitSingleton
                .getInstance()
                .create(APIService.class)
                .getArtist(artist)
                .enqueue(new Callback<ResultsBase>() {
                    @Override
                    public void onResponse(Call<ResultsBase> call, Response<ResultsBase> response) {
                        ResultsBase resultsBase = response.body();
                        List<ArtistModel> artistList = resultsBase.getResults();
                        Log.d(TAG, "onSuccess: " +  artistList.get(0).getArtistName());
                        callback.onArtistReceived(artistList);
                    }

                    @Override
                    public void onFailure(Call<ResultsBase> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}

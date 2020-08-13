package org.pursuit.heard.network;

import android.util.Log;

import org.pursuit.heard.model.Artist;
import org.pursuit.heard.model.ResultsBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistRepository {

    private static final String TAG = "EvelynActivity";

    public void networkCall(final String artist, final NetworkCallback callback) {
        RetrofitSingleton
                .getInstance()
                .create(APIService.class)
                .getArtist(artist)
                .enqueue(new Callback<ResultsBase>() {
                    @Override
                    public void onResponse(Call<ResultsBase> call, Response<ResultsBase> response) {
                        ResultsBase resultsBase = response.body();
                     //   List<Artist> artistList = resultsBase.getResults();
                        assert resultsBase != null;
                        Artist result = resultsBase.getResults().get(0);
                        Log.d(TAG, "onSuccess: " +  result.getArtistName());
                  //      callback.onArtistReceived(artistList);
                        callback.onArtistReceived(result);
                    }

                    @Override
                    public void onFailure(Call<ResultsBase> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}

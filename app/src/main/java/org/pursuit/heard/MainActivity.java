package org.pursuit.heard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.pursuit.heard.network.APIService;
import org.pursuit.heard.network.RetrofitSingleton;
import org.pursuit.heard.network.networkmodel.ArtistModel;
import org.pursuit.heard.network.networkmodel.ResultsBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import org.pursuit.heard.mainFragments.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private static final String TAG = "EvelynActivity";


   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Retrofit retrofit = RetrofitSingleton.getInstance();
        APIService apiService = retrofit.create(APIService.class);
        String editTextString = "lil wayne";
        final Call<ResultsBase> resultsBaseCall = apiService.getArtist(editTextString);

        resultsBaseCall.enqueue(new Callback<ResultsBase>() {
            @Override
            public void onResponse(Call<ResultsBase> call, Response<ResultsBase> response) {
                ResultsBase resultsBase = response.body();
                List<ArtistModel> artistList = resultsBase.getResults();
                Log.d(TAG, artistList.get(0).getArtistName());
            }

            @Override
            public void onFailure(Call<ResultsBase> call, Throwable t) {

            }
        });

 

    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

    }
}

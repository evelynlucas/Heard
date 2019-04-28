package org.pursuit.heard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.pursuit.heard.database.Artist;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.database.UserProfile;
import org.pursuit.heard.network.APIService;
import org.pursuit.heard.network.RetrofitSingleton;
import org.pursuit.heard.network.networkmodel.ArtistModel;
import org.pursuit.heard.network.networkmodel.ResultsBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import org.pursuit.heard.mainFragments.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private static final String TAG = "EvelynActivity";
    ProfileDatabase profileDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long id;
        profileDatabase = ProfileDatabase.getInstance(this);
        //profileDatabase.addProfile("naomyp");
        id = profileDatabase.getProfile("naomyp");
        Log.d(".MAINACTIVITY", "id: " + id);

        ArtistModel artistModel = new ArtistModel("Beyonce", "http");
        profileDatabase.addArtist(id, artistModel);

        List<ArtistModel> artist;
        artist = profileDatabase.getArtists(id);
        Log.d(".MAINACTIVITY", " artist: " + artist.get(0).getArtistName() + " artistURL: " + artist.get(0).getArtworkUrl100());

        final Retrofit retrofit = RetrofitSingleton.getInstance();
        APIService apiService = retrofit.create(APIService.class);
        final String editTextString = "lil wayne";
        final Call<ResultsBase> resultsBaseCall = apiService.getArtist(editTextString);

        resultsBaseCall.enqueue(new Callback<ResultsBase>() {
            @Override
            public void onResponse(Call<ResultsBase> call, Response<ResultsBase> response) {
                ResultsBase resultsBase = response.body();
                List<ArtistModel> artistList = resultsBase.getResults();
                Log.d(TAG, artistList.get(0).getArtistName());

                // UserProfile mainUserProfile = new UserProfile("NP@pursuit.org", 10, artistList);
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

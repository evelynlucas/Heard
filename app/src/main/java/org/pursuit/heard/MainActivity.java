package org.pursuit.heard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.pursuit.heard.mainFragments.AddArtistFragment;

import org.pursuit.heard.mainFragments.LoginFragment;
import org.pursuit.heard.mainFragments.MainUserFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainfragments_container, LoginFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openAddArtistFragment(Bundle bundle) {
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

    }

    @Override
    public void loginToMainFragment(String username) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainfragments_container, MainUserFragment.newInstance(username))
                .addToBackStack(null)
                .commit();
    }
}

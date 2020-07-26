package org.pursuit.heard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.pursuit.heard.mainFragments.AddArtistFragment;
import org.pursuit.heard.mainFragments.LoginFragment;
import org.pursuit.heard.mainFragments.MainUserFragment;

import org.pursuit.heard.mainFragments.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.mainfragments_container, LoginFragment.newInstance())
//                .addToBackStack(null)
//                .commit();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainfragments_container, MainUserFragment.newInstance("Alex"))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openAddArtistFragment(String username) {
        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.mainfragments_container, AddArtistFragment.newInstance(username))
        .addToBackStack(null)
        .commit();
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

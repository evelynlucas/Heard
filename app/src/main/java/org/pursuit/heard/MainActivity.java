package org.pursuit.heard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;

import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.fragments.AddArtistFragment;
import org.pursuit.heard.fragments.LoginFragment;
import org.pursuit.heard.fragments.MainUserFragment;

import org.pursuit.heard.fragments.OnFragmentInteractionListener;
import org.pursuit.heard.viewmodel.UserViewModel;
import org.pursuit.heard.viewmodel.UserViewModelFactory;

public class MainActivity extends AppCompatActivity  {

    private UserViewModel userViewModel;
    private ProfileDatabase profileDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.mainfragments_container, LoginFragment.newInstance())
//                .addToBackStack(null)
//                .commit();

    }

    private void initBackend() {
        Application application = this.getApplication();
        profileDatabase = ProfileDatabase.getInstance(getApplicationContext());
        UserViewModelFactory factory = new UserViewModelFactory(profileDatabase, application);
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
    }


//    @Override
//    public void loginToMainFragment(String username) {
//        initBackend();
//        userViewModel.setCurrentUser(username);
//        profileDatabase.addProfile(username);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.mainfragments_container, MainUserFragment.newInstance(userViewModel))
//                .addToBackStack(null)
//                .commit();
//    }
//
//    @Override
//    public void openAddArtistFragment(UserViewModel viewModel) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.mainfragments_container, AddArtistFragment.newInstance(userViewModel))
//                .addToBackStack(null)
//                .commit();
//    }

}

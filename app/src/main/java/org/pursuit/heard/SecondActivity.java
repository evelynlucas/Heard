package org.pursuit.heard;

import android.content.Intent;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import org.pursuit.heard.controller.ViewPagerAdapter;
import org.pursuit.heard.database.NearbyProfiles;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.network.networkmodel.ArtistModel;
import org.pursuit.heard.searchFragments.ViewPagerUsersFragment;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends FragmentActivity {

    private static final int SPLASH_TIME_OUT = 5000;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final View anim = findViewById(R.id.av7_xml);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createPagerFragments();
                anim.setVisibility(View.INVISIBLE);
            }
        }, SPLASH_TIME_OUT);
    }

    private void createPagerFragments() {
        ViewPager viewPager = findViewById(R.id.profiles_viewpager);
        NearbyProfiles nearbyProfiles = new NearbyProfiles();

        ProfileDatabase database = ProfileDatabase.getInstance(getApplicationContext());
        long id = database.getProfile(username);
        List<ArtistModel> myModels = database.getArtists(id);

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser1()));
        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser2()));
        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser3()));

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmentList));
    }


}

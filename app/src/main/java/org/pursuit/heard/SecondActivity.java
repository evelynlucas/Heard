package org.pursuit.heard;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import org.pursuit.heard.controller.ViewPagerAdapter;
import org.pursuit.heard.database.NearbyProfiles;
import org.pursuit.heard.searchFragments.LoadFragment;
import org.pursuit.heard.searchFragments.ViewPagerUsersFragment;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends FragmentActivity implements LoadFragment.MoveToVPFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profiles_viewpager, LoadFragment.newInstance())
                .commit();
    }


    @Override
    public void onNearbyFound() {
        ViewPager viewPager = findViewById(R.id.profiles_viewpager);
        NearbyProfiles nearbyProfiles = new NearbyProfiles();

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser1()));
        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser2()));
        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser3()));

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmentList));
    }
}

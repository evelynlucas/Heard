package org.pursuit.heard.fragments;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.heard.R;
import org.pursuit.heard.databinding.FragmentUserMatchBinding;
import org.pursuit.heard.recyclerview.ViewPagerAdapter;
import org.pursuit.heard.database.NearbyProfiles;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserMatchFragment extends Fragment {

    private FragmentUserMatchBinding binding;

    @SuppressLint("CheckResult")
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user_match, container, false);

        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    createPagerFragments();
                    binding.av7Xml.setVisibility(View.INVISIBLE);
                });
        return binding.getRoot();
    }


    private void createPagerFragments() {
        ViewPager viewPager = binding.profilesViewpager;
        NearbyProfiles nearbyProfiles = new NearbyProfiles();

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser1()));
        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser2()));
        fragmentList.add(ViewPagerUsersFragment.newInstance(nearbyProfiles.getUser3()));

        viewPager.setAdapter(new ViewPagerAdapter(getParentFragmentManager(), fragmentList));
    }


}

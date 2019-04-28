package org.pursuit.heard.searchFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.pursuit.heard.R;
import org.pursuit.heard.controller.ArtistPresentAdapter;
import org.pursuit.heard.database.Artist;
import org.pursuit.heard.database.UserProfile;
import org.pursuit.heard.network.networkmodel.ArtistModel;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


public class ViewPagerUsersFragment extends Fragment {

    private static final String USERNAME = "USERNAME";
    private static final String ARTISTS = "ARTISTS";

    private String otherUsername;
    private List<ArtistModel> artists;
    private View rootView;

    public ViewPagerUsersFragment() {}

    public static ViewPagerUsersFragment newInstance(UserProfile profile) {
        ViewPagerUsersFragment fragment = new ViewPagerUsersFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, profile.getUsername());
        args.putSerializable(ARTISTS, (Serializable) profile.getArtists());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            otherUsername = getArguments().getString(USERNAME);
            artists = (List<ArtistModel>) (getArguments().getSerializable(ARTISTS));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_pager_users, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView otherUsernameText = rootView.findViewById(R.id.viewPagerUser_profile_name);
        RecyclerView otherUserArtist = rootView.findViewById(R.id.recyclerView_container_viewPagerUserFragment);
        Button followButton = rootView.findViewById(R.id.viewPager_follow_button);

        otherUsernameText.setText(otherUsername);
        otherUserArtist.setLayoutManager(new LinearLayoutManager(requireContext()));
        otherUserArtist.setAdapter(new ArtistPresentAdapter(artists));

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "following " + otherUsername, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
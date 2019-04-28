package org.pursuit.heard.mainFragments;

import android.content.Context;
import android.content.Intent;
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

import org.pursuit.heard.R;
import org.pursuit.heard.SecondActivity;
import org.pursuit.heard.controller.ArtistPresentAdapter;
import org.pursuit.heard.database.UserProfile;
import org.pursuit.heard.network.networkmodel.ArtistModel;

import java.io.Serializable;
import java.util.List;


public class MainUserFragment extends Fragment {

    private static final String MAIN_USERNAME = "USER_MAIN";
    private static final String MAIN_ARTISTS = "ARTISTS_MAIN";

    private String mainUsername;
    private List<ArtistModel> mainArtists;
    private View rootView;
    private OnFragmentInteractionListener listener;

    public MainUserFragment() {}

    public static MainUserFragment newInstance(String mainUsername) {
        MainUserFragment fragment = new MainUserFragment();
        Bundle args = new Bundle();
        args.putString(MAIN_USERNAME, mainUsername);
        //    args.putSerializable(MAIN_ARTISTS, (Serializable) mainProfile.getArtistList());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mainUsername = getArguments().getString(MAIN_USERNAME);
            mainArtists = (List<ArtistModel>) getArguments().getSerializable(MAIN_ARTISTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_main_user, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView mainUsernameText = rootView.findViewById(R.id.userMain_profile_name);
        RecyclerView mainUserArtists = rootView.findViewById(R.id.recyclerView_container_mainUserFragment);
        Button findButton = rootView.findViewById(R.id.search_nearby_button);
        Button searchArtist = rootView.findViewById(R.id.search_artist_button);

        mainUsernameText.setText(mainUsername);
        mainUserArtists.setLayoutManager(new LinearLayoutManager(requireContext()));
        //     mainUserArtists.setAdapter(new ArtistPresentAdapter(mainArtists));

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        searchArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openAddArtistFragment(mainUsername);
            }
        });
    }
}

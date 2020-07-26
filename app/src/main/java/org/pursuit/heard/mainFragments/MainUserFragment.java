package org.pursuit.heard.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import org.pursuit.heard.R;
import org.pursuit.heard.SecondActivity;
import org.pursuit.heard.controller.ArtistPresentAdapter;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.network.networkmodel.ArtistModel;

import java.util.List;

public class MainUserFragment extends Fragment {

    private static final String MAIN_USERNAME = "USER_MAIN";

    private String mainUsername;
    private View rootView;
    private OnFragmentInteractionListener listener;

    public MainUserFragment() {}

    public static MainUserFragment newInstance(String mainUsername) {
        MainUserFragment fragment = new MainUserFragment();
        Bundle args = new Bundle();
        args.putString(MAIN_USERNAME, mainUsername);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mainUsername = getArguments().getString(MAIN_USERNAME);
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

        mainUsernameText.setText("Hello " + mainUsername);
        mainUserArtists.setLayoutManager(new LinearLayoutManager(requireContext()));
        ArtistPresentAdapter artistPresentAdapter = new ArtistPresentAdapter();
        mainUserArtists.setAdapter(artistPresentAdapter);

        ProfileDatabase database = ProfileDatabase.getInstance(view.getContext());
        long id = database.getProfile(mainUsername);
        List<ArtistModel> userModels = database.getArtists(id);
        artistPresentAdapter.setData(userModels);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), SecondActivity.class);
                intent.putExtra("USERNAME", mainUsername);
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

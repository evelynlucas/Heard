package org.pursuit.heard.mainFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.heard.R;
import org.pursuit.heard.controller.ArtistSearchAdapter;
import org.pursuit.heard.network.ArtistPresenter;
import org.pursuit.heard.network.NetworkCallback;
import org.pursuit.heard.network.networkmodel.ArtistModel;

import java.util.ArrayList;
import java.util.List;

public class AddArtistFragment extends Fragment implements SearchView.OnQueryTextListener{

    private List<ArtistModel> artistModels = new ArrayList<>();
    private View rootView;
    private String mainUsername;
    private static final String MAIN_USERNAME = "USER_MAIN";
    private ArtistSearchAdapter searchAdapter;

    public AddArtistFragment() {}

    public static AddArtistFragment newInstance(String mainUsername) {
        AddArtistFragment addArtistFragment = new AddArtistFragment();
        Bundle args = new Bundle();
        args.putString(MAIN_USERNAME, mainUsername);
        addArtistFragment.setArguments(args);
        return addArtistFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mainUsername = getArguments().getString(MAIN_USERNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_artist, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView artistSearchRV = rootView.findViewById(R.id.artist_search_recyclerview);
        artistSearchRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        searchAdapter = new ArtistSearchAdapter(artistModels);
        artistSearchRV.setAdapter(searchAdapter);
        SearchView artistSearch = rootView.findViewById(R.id.artist_searchview);
        artistSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String artist) {
        new ArtistPresenter().networkCall(artist, new NetworkCallback() {
            @Override
            public void onArtistReceived(List<ArtistModel> models) {
                searchAdapter.setData(models);
            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}

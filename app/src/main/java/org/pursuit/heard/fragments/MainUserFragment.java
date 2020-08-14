package org.pursuit.heard.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import org.jetbrains.annotations.NotNull;
import org.pursuit.heard.R;
import org.pursuit.heard.SecondActivity;
import org.pursuit.heard.controller.ArtistPresentAdapter;
import org.pursuit.heard.databinding.FragmentMainUserBinding;
import org.pursuit.heard.model.Artist;
import org.pursuit.heard.viewmodel.UserViewModel;

import java.util.List;

public class MainUserFragment extends Fragment {

    public static final String USER_VIEWMODEL = "USER_VIEW_MODEL";

    private UserViewModel viewModel;
    private String userName;
    private OnFragmentInteractionListener listener;
    private FragmentMainUserBinding binding;

    public MainUserFragment() {}

    public static MainUserFragment newInstance(UserViewModel viewModel) {
        MainUserFragment fragment = new MainUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_VIEWMODEL, viewModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = (UserViewModel) getArguments().getSerializable(USER_VIEWMODEL);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_main_user, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NotNull Context context) {
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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName = viewModel.getCurrentUser();
   //     TextView mainUsernameText = binding.userMainProfileName;
        RecyclerView mainUserArtists = binding.recyclerViewContainerMainUserFragment;

        setButtons();

   //     mainUsernameText.setText("Hello " + userName);
        mainUserArtists.setLayoutManager(new LinearLayoutManager(requireContext()));
        final ArtistPresentAdapter artistPresentAdapter = new ArtistPresentAdapter();
        mainUserArtists.setAdapter(artistPresentAdapter);

        MutableLiveData<List<Artist>> likedArtists = viewModel.getLikedArtists();
        likedArtists.observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                artistPresentAdapter.setData(artists);
                artistPresentAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setButtons() {
        Button findButton = binding.searchNearbyButton;
        Button searchArtist = binding.searchArtistButton;

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SecondActivity.class);
           //     intent.putExtra("USERNAME", userName);
                startActivity(intent);
            }
        });

        searchArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openAddArtistFragment(viewModel);
            }
        });
    }
}

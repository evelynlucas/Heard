package org.pursuit.heard.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.pursuit.heard.R;
import org.pursuit.heard.SecondActivity;
import org.pursuit.heard.model.Artist;
import org.pursuit.heard.recyclerview.ArtistPresentAdapter;
import org.pursuit.heard.databinding.FragmentMainUserBinding;
import org.pursuit.heard.viewmodel.UserViewModel;

import java.util.List;

public class MainUserFragment extends Fragment {

    private UserViewModel viewModel;
    private String userName;
    private FragmentMainUserBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_main_user, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName = viewModel.getUsername();
        binding.userMainProfileName.setText("Hello " + userName);
        RecyclerView mainUserArtists = binding.recyclerViewContainerMainUserFragment;

        setButtons();

        mainUserArtists.setLayoutManager(new LinearLayoutManager(requireContext()));
        final ArtistPresentAdapter artistPresentAdapter = new ArtistPresentAdapter();
        mainUserArtists.setAdapter(artistPresentAdapter);

        viewModel.fetchUserArtists();
        viewModel.getFollowedArtists().observe(getViewLifecycleOwner(), artists -> {
            artistPresentAdapter.setData(artists);
            artistPresentAdapter.notifyDataSetChanged();
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
                Navigation.findNavController(v).navigate(R.id.action_mainUserFragment_to_addArtistFragment);
            }
        });
    }
}

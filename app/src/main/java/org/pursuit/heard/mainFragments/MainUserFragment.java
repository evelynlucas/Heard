package org.pursuit.heard.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.pursuit.heard.R;
import org.pursuit.heard.SecondActivity;
import org.pursuit.heard.controller.ArtistPresentAdapter;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.databinding.FragmentMainUserBinding;
import org.pursuit.heard.model.Artist;
import org.pursuit.heard.viewmodel.UserViewModel;

import java.util.List;

public class MainUserFragment extends Fragment {

    private static final String MAIN_USERNAME = "USER_MAIN";

    private String mainUsername;
    private View rootView;
    private OnFragmentInteractionListener listener;
    private FragmentMainUserBinding binding;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView mainUsernameText = binding.userMainProfileName;
        RecyclerView mainUserArtists = binding.recyclerViewContainerMainUserFragment;
        Button findButton = binding.searchNearbyButton;
        Button searchArtist = binding.searchArtistButton;

  //      UserViewModel viewModel = ViewModelProvider.

        mainUsernameText.setText("Hello " + mainUsername);
        mainUserArtists.setLayoutManager(new LinearLayoutManager(requireContext()));
        ArtistPresentAdapter artistPresentAdapter = new ArtistPresentAdapter();
        mainUserArtists.setAdapter(artistPresentAdapter);

        ProfileDatabase database = ProfileDatabase.getInstance(view.getContext());
        long id = database.getProfile(mainUsername);
        List<Artist> userModels = database.getArtists(id);
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

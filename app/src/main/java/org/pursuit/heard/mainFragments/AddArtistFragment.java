package org.pursuit.heard.mainFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.pursuit.heard.R;
import org.pursuit.heard.controller.ArtistSearchAdapter;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.databinding.FragmentAddArtistBinding;
import org.pursuit.heard.network.ArtistRepository;
import org.pursuit.heard.network.NetworkCallback;
import org.pursuit.heard.model.Artist;
import org.pursuit.heard.viewmodel.UserViewModel;

import java.util.List;

public class AddArtistFragment extends Fragment implements SearchView.OnQueryTextListener{

    private UserViewModel viewModel;
    public static final String USER_VIEWMODEL = "USER_VIEW_MODEL";
    private FragmentAddArtistBinding binding;

    public AddArtistFragment() {}

    public static AddArtistFragment newInstance(UserViewModel viewModel) {
        AddArtistFragment fragment = new AddArtistFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_VIEWMODEL, viewModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = (UserViewModel) getArguments().getSerializable(USER_VIEWMODEL);        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_artist, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView artistSearch = binding.artistSearchview;
        artistSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String artist) {
        new ArtistRepository().networkCall(artist, new NetworkCallback() {
            @Override
            public void onArtistReceived(final Artist model) {
                binding.artistResultName.setText(model.getArtistName());

                Picasso.get().load(model.getArtworkUrl100()).into(binding.artistImage);

                binding.addArtistButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileDatabase database = ProfileDatabase.getInstance(v.getContext());
                        long id = database.getProfile(viewModel.getCurrentUser());
                        database.addArtist(id, model);

                        Log.e("README", "onSuccess" + id + ", " + model.getArtistName());
                    }
                });
            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}

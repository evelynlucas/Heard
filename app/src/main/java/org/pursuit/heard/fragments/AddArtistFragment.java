package org.pursuit.heard.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.squareup.picasso.Picasso;

import org.pursuit.heard.R;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.databinding.FragmentAddArtistBinding;
import org.pursuit.heard.network.ArtistSearchManager;
import org.pursuit.heard.viewmodel.UserViewModel;

public class AddArtistFragment extends Fragment implements SearchView.OnQueryTextListener {

    private UserViewModel viewModel;
    private ProfileDatabase database;
    private FragmentAddArtistBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_artist, container, false);
        binding.artistSearchview.setOnQueryTextListener(this);
        binding.artistCardView.setVisibility(View.GONE);
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        return binding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public boolean onQueryTextSubmit(String artist) {
        new ArtistSearchManager().networkCall(artist, model -> {
            binding.artistCardView.setVisibility(View.VISIBLE);
            binding.artistResultName.setText(model.getArtistName());
            Picasso.get().load(model.getArtworkUrl100()).into(binding.artistImage);


            RxView.clicks(binding.addArtistButton)
                    .subscribe(unit -> {
                        viewModel.addArtistForUser(model);
                    });

        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}

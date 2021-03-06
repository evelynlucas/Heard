package org.pursuit.heard.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.pursuit.heard.R;
import org.pursuit.heard.network.networkmodel.ArtistModel;
import org.pursuit.heard.view.ArtistSearchViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ArtistSearchAdapter extends RecyclerView.Adapter<ArtistSearchViewHolder> {

    private List<ArtistModel> models = new ArrayList<>();
    private String username;

    public ArtistSearchAdapter(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public ArtistSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ArtistSearchViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.artist_search_itemview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistSearchViewHolder artistSearchViewHolder, int position) {
        artistSearchViewHolder.onBind(models.get(position), username);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<ArtistModel> searchResults) {
        this.models = searchResults;
        notifyDataSetChanged();
    }
}

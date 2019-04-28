package org.pursuit.heard.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.pursuit.heard.R;
import org.pursuit.heard.network.networkmodel.ArtistModel;
import org.pursuit.heard.view.ArtistSearchViewHolder;

import java.util.List;

public class ArtistSearchAdapter extends RecyclerView.Adapter<ArtistSearchViewHolder> {

    private List<ArtistModel> models;

    public ArtistSearchAdapter(List<ArtistModel> models) {
        this.models = models;
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
        artistSearchViewHolder.onBind(models.get(position));
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

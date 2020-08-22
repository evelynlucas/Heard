package org.pursuit.heard.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.pursuit.heard.R;

import org.pursuit.heard.model.Artist;
import org.pursuit.heard.recyclerview.ArtistPresentViewHolder;

import java.util.ArrayList;
import java.util.List;


public class ArtistPresentAdapter extends RecyclerView.Adapter<ArtistPresentViewHolder> {

    private List<Artist> userArtists = new ArrayList<>();

    @NonNull
    @Override
    public ArtistPresentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ArtistPresentViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.artist_itemview,
                        viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistPresentViewHolder artistPresentViewHolder, int position) {
        artistPresentViewHolder.onBind(userArtists.get(position));
    }

    @Override
    public int getItemCount() {
        return userArtists.size();
    }

    public void setData(List<Artist> addArtist) {
        this.userArtists = addArtist;
        notifyDataSetChanged();
    }
}

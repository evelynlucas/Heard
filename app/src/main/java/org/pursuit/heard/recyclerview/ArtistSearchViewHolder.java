package org.pursuit.heard.recyclerview;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.pursuit.heard.R;
import org.pursuit.heard.database.ProfileDatabase;
import org.pursuit.heard.model.Artist;

public class ArtistSearchViewHolder extends RecyclerView.ViewHolder {

    private TextView artistResultName;
    private ImageView artistIcon;
    private Button addArtistButton;

    public ArtistSearchViewHolder(@NonNull View itemView) {
        super(itemView);

        artistResultName = itemView.findViewById(R.id.artist_result_name);
        artistIcon = itemView.findViewById(R.id.artist_image);
        addArtistButton = itemView.findViewById(R.id.add_artist_button);
    }

    public void onBind(final Artist artist, final String username) {
        artistResultName.setText(artist.getArtistName());
        Picasso.get().load(artist.getArtworkUrl100()).into(artistIcon);

        addArtistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDatabase database = ProfileDatabase.getInstance(v.getContext());
                long id = database.getProfile(username);
                database.addArtist(id, artist);

                Log.e("README", "onSuccess" + id + ", " + artist.getArtistName());
            }
        });
    }
}

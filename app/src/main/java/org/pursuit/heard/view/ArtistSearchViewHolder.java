package org.pursuit.heard.view;

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
import org.pursuit.heard.network.networkmodel.ArtistModel;

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

    public void onBind(final ArtistModel artistModel, final String username) {
        artistResultName.setText(artistModel.getArtistName());
        Picasso.get().load(artistModel.getArtworkUrl100()).into(artistIcon);

        addArtistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDatabase database = ProfileDatabase.getInstance(v.getContext());
                long id = database.getProfile(username);
                database.addArtist(id, artistModel);

                Log.e("README", "onSuccess" + id + ", " + artistModel.getArtistName());
            }
        });
    }
}

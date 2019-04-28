package org.pursuit.heard.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pursuit.heard.R;
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

    public void onBind(ArtistModel artistModel) {
        artistResultName.setText(artistModel.getArtistName());
        Picasso.get().load(artistModel.getArtworkUrl100()).into(artistIcon);
        addArtistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add to database
            }
        });
    }
}

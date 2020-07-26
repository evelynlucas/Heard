package org.pursuit.heard.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pursuit.heard.R;
import org.pursuit.heard.network.networkmodel.ArtistModel;

public class ArtistPresentViewHolder extends RecyclerView.ViewHolder {

    private TextView artistViewName;
    private ImageView artistViewImage;

    public ArtistPresentViewHolder(@NonNull View itemView) {
        super(itemView);

        artistViewName = itemView.findViewById(R.id.artistView_name);
        artistViewImage = itemView.findViewById(R.id.artistView_image);
    }

    public void onBind(ArtistModel artistModel) {
        artistViewName.setText(artistModel.getArtistName());
        Picasso.get().load(artistModel.getArtworkUrl100()).into(artistViewImage);
    }
}

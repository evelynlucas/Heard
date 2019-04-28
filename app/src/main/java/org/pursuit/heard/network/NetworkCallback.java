package org.pursuit.heard.network;

import org.pursuit.heard.network.networkmodel.ArtistModel;

import java.util.List;

public interface NetworkCallback {

    void onArtistReceived(List<ArtistModel> models);
}

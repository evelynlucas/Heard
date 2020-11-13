package org.pursuit.heard.network;

import org.pursuit.heard.model.Artist;

public interface NetworkCallback {
    void onArtistReceived(Artist model);
}

package org.pursuit.heard.network;

import org.pursuit.heard.model.Artist;

import java.util.List;

public interface NetworkCallback {

 //   void onArtistReceived(List<Artist> models);
    void onArtistReceived(Artist model);
}

package org.pursuit.heard.viewmodel;

import org.pursuit.heard.model.Artist;

import java.util.List;

public interface FetchArtistListener {

    void onArtistReceived(List<Artist> results);
}

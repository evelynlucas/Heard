package org.pursuit.heard.viewmodel;

import androidx.lifecycle.MutableLiveData;

import org.pursuit.heard.model.Artist;

import java.util.List;

public interface FetchArtistListener {

    void onArtistReceived(List<Artist> results);
}

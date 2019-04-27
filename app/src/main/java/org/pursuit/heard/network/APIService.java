package org.pursuit.heard.network;

import org.pursuit.heard.network.networkmodel.ArtistModel;
import org.pursuit.heard.network.networkmodel.ResultsBase;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("/search")
    Call<ResultsBase> getArtist(
            @Query("term") String term);
}

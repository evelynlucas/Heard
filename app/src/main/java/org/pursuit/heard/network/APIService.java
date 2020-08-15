package org.pursuit.heard.network;

import org.pursuit.heard.model.ResultsBase;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("/search")
    Single<ResultsBase> getArtist(
            @Query("term") String term);

}

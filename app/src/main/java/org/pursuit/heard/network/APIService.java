package org.pursuit.heard.network;

import org.pursuit.heard.model.ResultsBase;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

//    @GET("/search")
//    Call<ResultsBase> getArtist(
//            @Query("term") String term,
//            @Query("&entity") String entity);

    @GET("/search")
    Single<ResultsBase> getArtist(
            @Query("term") String term);

}

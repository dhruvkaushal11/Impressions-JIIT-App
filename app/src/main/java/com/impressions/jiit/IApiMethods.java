package com.impressions.jiit;

/**
 * Created by Dhruv on 12/25/2015.
 */

import retrofit.http.GET;
import retrofit.http.Query;

public interface IApiMethods {

    @GET("/newly_added.php")
    Curator getCurators(
            @Query("api_key") String key
    );
}
package com.impressions.jiit;

/**
 * Created by Dhruv on 12/25/2015.
 */

import retrofit.http.GET;
import retrofit.http.Query;

public interface IApiMethods_popular {

    @GET("/popular_event.php")
    Curator getCurators(
            @Query("api_key") String key
    );
}
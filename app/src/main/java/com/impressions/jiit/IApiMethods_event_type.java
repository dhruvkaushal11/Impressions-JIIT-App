package com.impressions.jiit;

/**
 * Created by Dhruv on 12/25/2015.
 */

import retrofit.http.GET;
import retrofit.http.Query;

public interface IApiMethods_event_type {

    @GET("/event_type.php")
    Curator_event_type getCurators(
            @Query("api_key") String key
    );
}
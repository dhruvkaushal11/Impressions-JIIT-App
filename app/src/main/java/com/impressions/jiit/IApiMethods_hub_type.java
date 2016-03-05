package com.impressions.jiit;

/**
 * Created by Dhruv on 12/25/2015.
 */

import retrofit.http.GET;
import retrofit.http.Query;

public interface IApiMethods_hub_type {

    @GET("/hub_type.php")
    Curator_hub_type getCurators(
            @Query("api_key") String key
    );
}
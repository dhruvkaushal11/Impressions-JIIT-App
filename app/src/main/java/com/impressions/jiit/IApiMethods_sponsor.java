package com.impressions.jiit;

/**
 * Created by Dhruv on 12/25/2015.
 */

import retrofit.http.GET;
import retrofit.http.Query;

public interface IApiMethods_sponsor {

    @GET("/sponsor.php")
    Curator_sponsor getCurators(
            @Query("api_key") String key
    );
}
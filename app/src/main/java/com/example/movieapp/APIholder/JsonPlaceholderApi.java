package com.example.movieapp.APIholder;

import com.example.movieapp.Database.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceholderApi {

    @GET("3/movie/top_rated")
    Call<JsonResponse> getTopRated(@Query("api_key") String apiKey);

    @GET("3/movie/popular")
    Call<JsonResponse> getPopular(@Query("api_key") String apiKey);
}

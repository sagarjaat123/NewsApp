package com.example.newsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("v2/top-headlines")
    Call<Data>getDetail(@Query("country") String country,
                        @Query("category") String cate,
                        @Query("apiKey") String key);
}

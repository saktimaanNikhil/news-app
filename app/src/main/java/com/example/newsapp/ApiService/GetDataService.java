package com.example.newsapp.ApiService;

import com.example.newsapp.modles.ApiModel;
import com.example.newsapp.modles.mainNews;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GetDataService {
    @GET("top-headlines")
    Call<mainNews> getApiModel(
            @QueryMap Map<String, String> parameters
    );
}

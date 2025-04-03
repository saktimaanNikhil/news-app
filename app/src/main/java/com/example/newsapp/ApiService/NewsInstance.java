package com.example.newsapp.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsInstance {
    private static NewsInstance clientObject;
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://newsapi.org//v2/";


    NewsInstance() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    public static synchronized NewsInstance getInstance() {
        if (clientObject == null) {
            clientObject = new NewsInstance();
        }
        return clientObject;
    }


    public GetDataService getApi() {
        return retrofit.create(GetDataService.class);
    }
}

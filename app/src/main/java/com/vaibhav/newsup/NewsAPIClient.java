package com.vaibhav.newsup;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsAPIClient {

    public static final String BASE_URL = "https://newsapi.org/";
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(){

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

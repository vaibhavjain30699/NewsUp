package com.vaibhav.newsup.network;

public class AppUtils {

    public AppUtils() {}

    public static AppInterface getApiService(){

        return NewsAPIClient.getRetrofitClient().create(AppInterface.class);

    }
}

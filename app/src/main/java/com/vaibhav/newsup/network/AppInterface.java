package com.vaibhav.newsup.network;

import com.vaibhav.newsup.models.TopHeadlines;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppInterface {

    @GET("v2/top-headlines?category=general&country=in&pagesize=100&apiKey=1d624e90a4b74c5db6c69f8ec1f82351")
    Call<TopHeadlines> getGeneralNews();

    @GET("v2/top-headlines?category=entertainment&country=in&pagesize=100&apiKey=1d624e90a4b74c5db6c69f8ec1f82351")
    Call<TopHeadlines> getEntertainmentNews();

    @GET("v2/top-headlines?category=technology&country=in&pagesize=100&apiKey=1d624e90a4b74c5db6c69f8ec1f82351")
    Call<TopHeadlines> getTechnologyNews();

    @GET("v2/top-headlines?category=sports&country=in&pagesize=100&apiKey=1d624e90a4b74c5db6c69f8ec1f82351")
    Call<TopHeadlines> getSportsNews();

    @GET("v2/top-headlines?category=science&country=in&pagesize=100&apiKey=1d624e90a4b74c5db6c69f8ec1f82351")
    Call<TopHeadlines> getScienceNews();

    @GET("v2/top-headlines?category=health&country=in&pagesize=100&apiKey=1d624e90a4b74c5db6c69f8ec1f82351")
    Call<TopHeadlines> getHealthNews();

    //@GET("v2/everything?q=bitcoin&apiKey=1d624e90a4b74c5db6c69f8ec1f82351")

}

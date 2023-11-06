package com.example.roomradar.api;

import com.example.roomradar.model.AddPostData;
import com.example.roomradar.model.Post;
import com.example.roomradar.model.PostRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    Gson gson = new GsonBuilder().create();
    APIService apiservice = new Retrofit.Builder().baseUrl("https://be-prm-v4.onrender.com/api/v1/").addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIService.class);

    @POST("post")
    Call<AddPostData> addNewPost(@Body AddPostData addPostData);

    @GET("post")
    Call<PostRes> getAllPost();
}

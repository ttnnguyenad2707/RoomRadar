package com.example.roomradar.api;

import com.example.roomradar.model.AddPostData;
import com.example.roomradar.model.Post;
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
    APIService apiservice = new Retrofit.Builder().baseUrl("https://roomradar.onrender.com/api/v1/").addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIService.class);

    @GET("post/getAll/{quantity}")
    Call<Post> getPost(@Path("quantity") int quantity);

    @POST("post")
    Call<AddPostData> addNewPost(@Body AddPostData addPostData);
}

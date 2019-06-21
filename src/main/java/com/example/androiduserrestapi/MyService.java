package com.example.androiduserrestapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyService {

    @GET("users/")
    Call<List<User>> getUsers();

    @GET("users/{user_id}")
    Call<User> getUser(@Path("user_id") int user_id);

    @DELETE("users/{user_id}")
    Call<Void> deleteUser(@Path("user_id") int user_id);

    @POST("users/")
    Call<User> newUser(@Body User user);
}

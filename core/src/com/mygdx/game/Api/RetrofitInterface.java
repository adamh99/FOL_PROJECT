package com.mygdx.game.Api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/authPost")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/register")
    Call<Void> executeSignup(@Body HashMap<String, String> map);

    @POST("/getSettingsPost")
    Call<SettingsResult> getGameSettings(@Body HashMap<String, String> map);

    @POST ("/saveStats")
    Call<Void> executeSaveStats(@Body HashMap<String, Integer> map);


}

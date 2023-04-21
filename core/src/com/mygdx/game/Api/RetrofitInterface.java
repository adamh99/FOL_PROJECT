package com.mygdx.game.Api;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/authPost")
    Call<Void> executeLogin(@Body HashMap<String, String> map);

    @POST("/registerUserAndroid")
    Call<Void> executeSignup(@Body HashMap<String, String> map);

    @POST("/getSettingsPost")
    Call<SettingsResult> getGameSettings(@Body HashMap<String, String> map);

    @POST ("/saveStats")
    Call<Void> executeSaveStats(@Body HashMap<String, Integer> map);


}

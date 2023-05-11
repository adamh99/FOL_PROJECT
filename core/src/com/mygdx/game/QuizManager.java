package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Api.RetrofitInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizManager {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL="http://localhost:3012";

    public void setResponse(Response<Question[]> response) {
        this.response = response;
    }

    public Response<Question[]> response = null;
    public Question[] fetchQuestionsFromServer() throws IOException {
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface=retrofit.create(RetrofitInterface.class);

        Call<Question[]> call = retrofitInterface.fetchQuestions();
       /* call.enqueue(new Callback<Question[]>() {

            @Override
            public void onResponse(Call<Question[]> call, Response<Question[]> response) {
                System.out.println("Fetched questions: "+response.body());
                setResponse(response);
            }

            @Override
            public void onFailure(Call<Question[]> call, Throwable t) {

            }



        });*/
        response = call.execute();
        System.out.println(response.body()[0].toString()+"Ó´´OÓ");

        return response.body();

    }

    public void startQuiz(){

    }
    public void answerQuestion(){

    }

    public void closeQuiz(){

    }
}

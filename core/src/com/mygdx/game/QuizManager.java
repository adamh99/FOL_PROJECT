package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Api.RetrofitInterface;
import com.mygdx.game.Screens.PopupDialogScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizManager {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL="http://localhost:3012";

    List<Question> questions;

    PopupDialogScreen popupDialogScreen;

    public void setResponse(Response<Question[]> response) {
        this.response = response;
    }

    public Response<Question[]> response = null;
    public List<Question> fetchQuestionsFromServer() throws IOException {
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
        //System.out.println("RESPONSE: "+response.body()[0].toString());
        questions = questionsBySubject(response.body(),"CONCEPTOS BÁSICOS SOBRE PREVENCIÓN DE RIESGOS LABORALES");
        return questions;
    }

    public void startQuiz(){
        while (!questions.isEmpty()){
            //RECORRE LAS QUESTIONS
            //displayQuestionDialog();
        }
    }

    public List<Question> questionsBySubject(Question[] questions,String subject){
        List<Question> subjectQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getSubject().equals(subject)){
                subjectQuestions.add(question);
            }
        }
        return subjectQuestions;
    }
    public void answerQuestion(){

    }

    public void closeQuiz(){

    }
    /*public void displayPopUpDialog(String title, String message, PopupDialogScreen.EnumClass.Positions positions){
		popupscreen = new PopupDialogScreen(title,message,this,positions.CENTER,stage);
		popUp = true;
	}*/
    public void displayQuestionDialog(GameScreen underlying,PopupDialogScreen.EnumClass.Positions positions, Stage stage){
        popupDialogScreen = new PopupDialogScreen(questions,underlying,positions.CENTER,stage);
    }
}

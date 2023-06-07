package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Api.RetrofitInterface;
import com.mygdx.game.Screens.PopupDialogScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizManager {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL="http://localhost:3012";

    List<Question> questions;

    PopupDialogScreen popupDialogScreen;
    private int totalPoints;
    private int score;

    public QuizManager() {
        totalPoints = 0;
        score = 0;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getScore() {
        return score;
    }

    public void setResponse(Response<Question[]> response) {
        this.response = response;
    }

    public Response<Question[]> response = null;
    public Question[] fetchQuestionsFromServer() throws IOException, InterruptedException {
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface=retrofit.create(RetrofitInterface.class);

        Call<Question[]> call = retrofitInterface.fetchQuestions();

        response = call.execute();

        return response.body();
    }

    public void startQuiz(Question[] questions, GameScreen underlying, Stage stage, String uniqueSubject) {
        underlying.popUp = true;
        Question[] formatedQuestions = questionsBySubject(questions, uniqueSubject);

        for (int i = 0; i < formatedQuestions.length; i++) {
            underlying.currentQuiz.add(formatedQuestions[i]);
        }
        underlying.currentPopUp = new PopupDialogScreen(underlying.currentQuiz.pop(), underlying, PopupDialogScreen.EnumClass.Positions.CENTER, stage);
    }

    public void quizzScreen(Question[] questions, GameScreen underlying, Stage stage) {
        underlying.popUp = true;
        underlying.currentPopUp = new PopupDialogScreen("QUIZZ", questions, underlying, PopupDialogScreen.EnumClass.Positions.CENTER, stage);
    }

    public Question[] questionsBySubject(Question[] questions, String subject) {
        List<Question> subjectQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getSubject().equals(subject)) {
                subjectQuestions.add(question);
            }
        }

        Question[] resultArray = new Question[subjectQuestions.size()];
        subjectQuestions.toArray(resultArray);
        return resultArray;
    }

    public void answerQuestion() {

    }

    public void closeQuiz() {

    }

    public void displayQuestionDialog(Question q, GameScreen underlying, PopupDialogScreen.EnumClass.Positions positions, Stage stage) {
        popupDialogScreen = new PopupDialogScreen(q, underlying, positions.CENTER, stage);

        underlying.popUp = true;
        popupDialogScreen.show();
    }
}

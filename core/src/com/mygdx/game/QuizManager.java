package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Api.RetrofitInterface;
import com.mygdx.game.Screens.GameOverScreen;
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
    private String BASE_URL = "http://localhost:3012";

    private List<Question> questions;
    private PopupDialogScreen popupDialogScreen;
    private int totalPoints;
    private int score;
    QuizManager quizManager;
    GameOverScreen gameOverScreen;
    MyFolGame Game;

    private int consecutivefails;

    public QuizManager() {
        questions = new ArrayList<>();
        score=0;
        totalPoints=0;
        quizManager = this;
        consecutivefails=0;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getScore() {
        return score;
    }

    public Question[] fetchQuestionsFromServer() throws IOException, InterruptedException {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Question[]> call = retrofitInterface.fetchQuestions();

        Response<Question[]> response = call.execute();

        if (response.isSuccessful()) {
            Question[] questionArray = response.body();
            if (questionArray != null) {
                questions.clear();
                for (Question question : questionArray) {
                    questions.add(question);
                }
                return questionArray;
            }
        }

        return null;
    }

    public void startQuiz(Question[] questions, GameScreen underlying, Stage stage, String uniqueSubject) {
        underlying.popUp = true;
        Question[] formattedQuestions = questionsBySubject(questions, uniqueSubject);

        for (Question question : formattedQuestions) {
            underlying.currentQuiz.add(question);
        }
        underlying.currentPopUp = new PopupDialogScreen(underlying.currentQuiz.pop(), underlying, PopupDialogScreen.EnumClass.Positions.CENTER, stage, this);
    }

    public void quizzScreen(Question[] questions, GameScreen underlying, Stage stage) {
        underlying.popUp = true;
        underlying.currentPopUp = new PopupDialogScreen("QUIZ", questions, underlying, PopupDialogScreen.EnumClass.Positions.CENTER, stage);
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
/*android*/
    public void answerQuestion(Boolean correct) {
        if (correct) {
            totalPoints += 10;
            score++;
            consecutivefails = 0;
        }else{
            consecutivefails++;
            if (consecutivefails>=2){
                closeQuiz();
            }
        }
    }

    public void closeQuiz() {
        GameOverScreen gameOverScreen =  new GameOverScreen(Game,getScore(),getTotalPoints());
        ((Game) Gdx.app.getApplicationListener()).setScreen(gameOverScreen);
    }

    public void displayQuestionDialog(Question q, GameScreen underlying, PopupDialogScreen.EnumClass.Positions positions, Stage stage) {
        popupDialogScreen = new PopupDialogScreen(q, underlying, positions.CENTER, stage,this);

        underlying.popUp = true;
        popupDialogScreen.show();
    }
}
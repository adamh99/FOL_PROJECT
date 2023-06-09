package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Question;
import com.mygdx.game.QuizManager;
import com.mygdx.game.Settings.AssetLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PopupDialogScreen implements Screen {

    public static class EnumClass {

        public enum Positions {
            TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT, CENTER
        }
    }
    public Question[] questions;
    private Dialog dialog;
    private Stage stage;
    private Skin skin = AssetLoader.skin;
    private String message,title,selectedOption;
    String validOption ="";
    boolean correct = false;
    private List<TextButton> linkButtons;
    TextButton buttonA,buttonB,buttonC;
    GameScreen underlying;
    public final List<Vector2> VECTORS = new ArrayList<>();
    float height = Gdx.graphics.getHeight();
    float width = Gdx.graphics.getWidth();

    QuizManager quizManager;
    public PopupDialogScreen(final Question question, GameScreen underlying, EnumClass.Positions positions, final Stage stage, final QuizManager quizManager) {
        final GameScreen underlyingFinal = underlying;
        this.selectedOption = "";
        System.out.println("POPUPDIALOGSCREEN "+question.toString());
        this.title = question.getSubject();
        this.message = question.getTitle();
        this.underlying = underlying;
        this.underlying.popUp = true;
        this.stage=stage;
        this.validOption = question.getValidOption();
        Gdx.input.setInputProcessor(stage);
        this.quizManager= quizManager;
        //0=TOP RIGHT 1=BOTTOM RIGHT 2=BOTTOM LEFT 3=TOP LEFT
        Integer i = null;
        VECTORS.add(new Vector2(width,height));
        VECTORS.add(new Vector2(width,height*0.001f));
        VECTORS.add(new Vector2(width*0.001f,height*0.01f));
        VECTORS.add(new Vector2(width*0.001f,height));
        //position

        buttonA = new TextButton(question.getOptions()[0],skin);
        buttonB = new TextButton(question.getOptions()[1],skin);
        buttonC = new TextButton(question.getOptions()[2],skin);

        // create and add the dialog to the stage
        dialog = new Dialog(title, skin);
        dialog.text(message);
        dialog.getContentTable().row();
        dialog.getContentTable().add(buttonA);
        dialog.getContentTable().row();
        dialog.getContentTable().add(buttonB);
        dialog.getContentTable().row();
        dialog.getContentTable().add(buttonC);

        buttonA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedOption= buttonA.getText().toString();
                correct = selectedOption.equals(validOption);
                    if(correct){

                        quizManager.answerQuestion(true);
                        dialog.remove();
                        underlyingFinal.popUp= false;
                        Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
                        AssetLoader.rightsound.play();
                        if(!underlyingFinal.currentQuiz.empty()) {
                            underlyingFinal.currentPopUp = new PopupDialogScreen(underlyingFinal.currentQuiz.pop(), underlyingFinal, PopupDialogScreen.EnumClass.Positions.CENTER, stage,quizManager);
                        }
                    }else{
                        quizManager.answerQuestion(false);
                        AssetLoader.wrongsound.play();

                    }
                }


        });
        buttonB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedOption= buttonB.getText().toString();
                /*
                System.out.println("SelectedOption "+selectedOption);
                System.out.println("ValidOption "+validOption);
                System.out.println("EL IFFFFFF     "+selectedOption.equals(question[0].getValidOption()));
                */
                correct = selectedOption.equals(validOption);
                if(correct){

                    quizManager.answerQuestion(true);
                        dialog.remove();
                        AssetLoader.rightsound.play();
                        underlyingFinal.popUp= false;
                        Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
                    if(!underlyingFinal.currentQuiz.empty()) {
                        underlyingFinal.currentPopUp = new PopupDialogScreen(underlyingFinal.currentQuiz.pop(), underlyingFinal, PopupDialogScreen.EnumClass.Positions.CENTER, stage,quizManager);
                    }
                }else{
                    quizManager.answerQuestion(false);
                    AssetLoader.wrongsound.play();
                }
            }


        });
        buttonC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedOption= buttonC.getText().toString();
                /*
                System.out.println("SelectedOption "+selectedOption);
                System.out.println("ValidOption "+validOption);
                System.out.println("EL IFFFFFF     "+selectedOption.equals(question[0].getValidOption()));
                */
                correct = selectedOption.equals(validOption);
                if(correct){

                    quizManager.answerQuestion(true);
                    dialog.remove();
                    AssetLoader.rightsound.play();
                    underlyingFinal.popUp= false;
                    Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
                    if(!underlyingFinal.currentQuiz.empty()) {
                        underlyingFinal.currentPopUp = new PopupDialogScreen(underlyingFinal.currentQuiz.pop(), underlyingFinal, PopupDialogScreen.EnumClass.Positions.CENTER, stage,quizManager);

                    }

                }else{
                    quizManager.answerQuestion(false);
                    AssetLoader.wrongsound.play();
                }
            }
        });

        switch (positions) {
            case TOP_RIGHT:i = 0;
                break;
            case BOTTOM_RIGHT:i = 1;
                break;
            case BOTTOM_LEFT:i = 2;
                break;
            case TOP_LEFT:i = 3;
                break;
            case CENTER:
                break;
        }
        if(i!=null){
            dialog.setPosition(VECTORS.get(i).x,VECTORS.get(i).y);

        }else{
            dialog.show(stage);
        }

        stage.addActor(dialog);

    }

    public PopupDialogScreen(String title, final Question[] questions, final GameScreen underlying, EnumClass.Positions positions, final Stage stage) {

        //this.message = message;
        this.questions = questions;
        this.underlying = underlying;
        this.stage=stage;
        linkButtons = new ArrayList<>();
        Gdx.input.setInputProcessor(stage);

        final String[] uniqueSubjects = getUniqueSubjects(questions);


        //0=TOP RIGHT 1=BOTTOM RIGHT 2=BOTTOM LEFT 3=TOP LEFT
        VECTORS.add(new Vector2(width,height));
        VECTORS.add(new Vector2(width,height*0.001f));
        VECTORS.add(new Vector2(width*0.001f,height*0.01f));
        VECTORS.add(new Vector2(width*0.001f,height));
        Integer i = null;

        // create and add the dialog to the stage
        dialog = new Dialog(title, skin);
        for (String str : uniqueSubjects){
            addSubject(str);
        }

        dialog.add();

        stage.addActor(dialog);

        switch (positions) {
            case TOP_RIGHT:i = 0;
                break;
            case BOTTOM_RIGHT:i = 1;
                break;
            case BOTTOM_LEFT:i = 2;
                break;
            case TOP_LEFT:i = 3;
                break;
            case CENTER:
                break;
        }
        if(i==null){
                dialog.show(stage);
        }else{
            dialog.setPosition(VECTORS.get(i).x,VECTORS.get(i).y);
        }
        //dialog.setSize(300, 300);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
       //pintar toda la pantalla de un color Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();




    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        underlying.popUp = false;
    }

    public static String[] getUniqueSubjects(Question[] array) {
        String[] strings = new String[array.length];
        for (int i = 0;i<array.length;i++){
            strings[i] = array[i].getSubject();
        }
        /*
        for (int i=0;i< strings.length;i++){
            System.out.println("SUBJECTS "+strings[i]);
        }*/

        Set<String> uniqueStrings = new HashSet<>();
        for (String str : strings) {
            if (!uniqueStrings.contains(str)){
                uniqueStrings.add(str);
            }
        }
        /*
        for (String str : uniqueStrings){
            System.out.println("UNIQUE "+str);
        }*/
        return uniqueStrings.toArray(new String[0]);
    }
    public TextButton addSubject(final String uniqueSubject){
        TextButton button = new TextButton(uniqueSubject,skin);
        linkButtons.add(button);
        for(TextButton t : linkButtons){
            dialog.getContentTable().add(t);
            dialog.getContentTable().row();
            t.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    underlying.qmanager.startQuiz(questions,underlying,stage,uniqueSubject);
                }
            });
        }

        return button;
    }
}
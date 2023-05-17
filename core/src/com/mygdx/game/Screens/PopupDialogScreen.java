package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Message;
import com.mygdx.game.Question;
import com.mygdx.game.Settings.AssetLoader;

import java.util.ArrayList;
import java.util.List;

public class PopupDialogScreen implements Screen {

    public static class EnumClass {

        public enum Positions {
            TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT, CENTER
        }
    }
    private Dialog dialog;
    private Stage stage;
    private Skin skin = AssetLoader.skin;
    private String message,title,selectedOption;
    String validOption ="";
    boolean correct = false;
    private TextButton closeButton;
    TextButton buttonA,buttonB,buttonC;
    GameScreen underlying;
    public final List<Vector2> VECTORS = new ArrayList<>();
    float height = Gdx.graphics.getHeight();
    float width = Gdx.graphics.getWidth();
    public PopupDialogScreen(Question question, GameScreen underlying, EnumClass.Positions positions, final Stage stage) {
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
                /*
                System.out.println("SelectedOption "+selectedOption);
                System.out.println("ValidOption "+validOption);
                System.out.println("EL IFFFFFF     "+selectedOption.equals(question[0].getValidOption()));
                */
                correct = selectedOption.equals(validOption);
                    if(correct){
                        dialog.remove();
                        underlyingFinal.popUp= false;
                        Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
                        AssetLoader.rightsound.play();
                        if(!underlyingFinal.currentQuiz.empty()) {
                            underlyingFinal.currentPopUp = new PopupDialogScreen(underlyingFinal.currentQuiz.pop(), underlyingFinal, PopupDialogScreen.EnumClass.Positions.CENTER, stage);
                        }
                    }else{
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
                        dialog.remove();
                        AssetLoader.rightsound.play();
                        underlyingFinal.popUp= false;
                        Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
                    if(!underlyingFinal.currentQuiz.empty()) {
                        underlyingFinal.currentPopUp = new PopupDialogScreen(underlyingFinal.currentQuiz.pop(), underlyingFinal, PopupDialogScreen.EnumClass.Positions.CENTER, stage);
                    }
                }else{
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
                    dialog.remove();
                    AssetLoader.rightsound.play();
                    underlyingFinal.popUp= false;
                    Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
                    if(!underlyingFinal.currentQuiz.empty()) {
                        underlyingFinal.currentPopUp = new PopupDialogScreen(underlyingFinal.currentQuiz.pop(), underlyingFinal, PopupDialogScreen.EnumClass.Positions.CENTER, stage);

                    }

                }else{
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

  /*  public PopupDialogScreen(String title, String message, final GameScreen underlying, EnumClass.Positions positions, final Stage stage) {


        this.message = message;
        this.underlying = underlying;
        this.stage=stage;
        Gdx.input.setInputProcessor(stage);


        //0=TOP RIGHT 1=BOTTOM RIGHT 2=BOTTOM LEFT 3=TOP LEFT
        VECTORS.add(new Vector2(width,height));
        VECTORS.add(new Vector2(width,height*0.001f));
        VECTORS.add(new Vector2(width*0.001f,height*0.01f));
        VECTORS.add(new Vector2(width*0.001f,height));
        Integer i = null;

        // create and add the dialog to the stage
        dialog = new Dialog(title, skin);

        Label messageLabel = new Label(message, skin);
        closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
                underlying.popUp= false;
                Gdx.input.setInputProcessor(underlying.myInputProcessor);
            }
        });

        dialog.text(messageLabel);
        dialog.add();
        dialog.button(closeButton, true);

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


    }*/

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

    public TextButton getCloseButton() {
        return closeButton;
    }
}
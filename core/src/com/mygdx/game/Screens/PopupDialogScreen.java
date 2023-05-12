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

    private Question[] question;
    public PopupDialogScreen(final Question[] question, GameScreen underlying, EnumClass.Positions positions, Stage stage) {
        final GameScreen underlyingFinal = underlying;
        this.selectedOption = "";
        this.title = question[0].getSubject();
        this.message = question[0].getTitle();
        this.underlying = underlying;
        this.stage=stage;
        this.validOption = question[0].getValidOption();
        Gdx.input.setInputProcessor(stage);
        //0=TOP RIGHT 1=BOTTOM RIGHT 2=BOTTOM LEFT 3=TOP LEFT
        Integer i = null;
        VECTORS.add(new Vector2(width,height));
        VECTORS.add(new Vector2(width,height*0.001f));
        VECTORS.add(new Vector2(width*0.001f,height*0.01f));
        VECTORS.add(new Vector2(width*0.001f,height));
        //position


        buttonA = new TextButton(question[0].getOptions()[0],skin);
        buttonB = new TextButton(question[0].getOptions()[1],skin);
        buttonC = new TextButton(question[0].getOptions()[2],skin);

        // create and add the dialog to the stage
        dialog = new Dialog(title, skin);
        dialog.text(message);
        dialog.add();
        dialog.button(buttonA);
        dialog.button(buttonB);
        dialog.button(buttonC);
        buttonA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("buttonTOSTRING"+buttonA.getText().toString());
                System.out.println("VALIDOPTIONCOJONE"+validOption);
                selectedOption= buttonA.getText().toString();
                System.out.println("EL IFFFFFF     "+selectedOption.equals(question[0].getValidOption()));
                correct = selectedOption.equals(validOption);
                    if(correct){
                        dialog.remove();
                        underlyingFinal.popUp= false;
                        Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
                    }else {
                        System.out.println("PUTAS");
                    }
                }


        });
        buttonB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                    System.out.println(buttonB.toString());
                    System.out.println(question[0].getValidOption());
                    selectedOption= buttonB.toString();
                    if(selectedOption.equals(question[0].getValidOption())){
                        dialog.remove();
                        underlyingFinal.popUp= false;
                        Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
                    }
                }


        });
        buttonC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    System.out.println(buttonC.toString());
                    System.out.println(question[0].getValidOption());
                    selectedOption= buttonC.toString();
                    if(selectedOption.equals(question[0].getValidOption())){
                        dialog.remove();
                        underlyingFinal.popUp= false;
                        Gdx.input.setInputProcessor(underlyingFinal.myInputProcessor);
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
        if(i==null){
            dialog.show(stage);
        }else{
            dialog.setPosition(VECTORS.get(i).x,VECTORS.get(i).y);
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
        stage.dispose();
    }

    public TextButton getCloseButton() {
        return closeButton;
    }
}
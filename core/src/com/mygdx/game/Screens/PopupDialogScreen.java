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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameScreen;
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
    private String message;
    private TextButton closeButton;

    GameScreen underlying;
    public final List<Vector2> VECTORS = new ArrayList<>();
    float height = Gdx.graphics.getHeight();
    float width = Gdx.graphics.getWidth();


    public PopupDialogScreen(String title, String message, final GameScreen underlying, EnumClass.Positions positions, Stage stage) {


        this.message = message;
        this.underlying = underlying;
        this.stage=stage;


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
                System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                dialog.remove();
                underlying.popUp= false;
            }
        });

        dialog.text(messageLabel);
        dialog.add();
        dialog.button(closeButton, true);

        stage.addActor(dialog);

        switch (positions) {
            case TOP_RIGHT: {
                i = 0;
                break;
            }
            case BOTTOM_RIGHT:i = 1;
                break;
            case BOTTOM_LEFT:i = 2;
                break;
            case TOP_LEFT:i = 3;
                break;
            case CENTER:;
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
        stage.dispose();
    }

    public TextButton getCloseButton() {
        return closeButton;
    }
}
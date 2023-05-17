package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyFolGame;
import com.mygdx.game.Settings.AssetLoader;

public class SplashScreen implements Screen {

    private static Texture splashtexture;
    private Stage splashstage;
    private float WIDTH = Gdx.graphics.getWidth(),HEIGHT = Gdx.graphics.getHeight();
    SpriteBatch batch_nuevo;
    float tiempo;

    MyFolGame fl;

    public SplashScreen(MyFolGame fl){
        super();
        this.fl=fl;
    }
    @Override
    public void show() {
        splashtexture= new Texture(Gdx.files.internal("LOGO.png"));

        batch_nuevo= new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        tiempo+=delta;

        Gdx.gl.glClearColor(1,1,1,1)
        ;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(tiempo<5){
            float imageWidht= Gdx.graphics.getWidth()*0.5f;
            float imageHeight = Gdx.graphics.getHeight()*0.5f;
            batch_nuevo.begin();
            batch_nuevo.draw(splashtexture,(Gdx.graphics.getWidth()-imageWidht)/2,(Gdx.graphics.getHeight()-imageHeight)/2, imageWidht,imageHeight);


            batch_nuevo.end();
        }else{ fl.setScreen(new TitleScreen(fl));
        }

    }

    @Override
    public void resize(int width, int height) {

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

    }
}

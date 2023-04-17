package com.mygdx.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyFolGame;
import com.mygdx.game.Settings.AssetManager;

public class TitleScreen extends ApplicationAdapter implements Screen {
    private MyFolGame Game;
    private Stage stage;

    public TitleScreen(MyFolGame game) {
        this.Game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        buildMenuTable();
    }

    private TextButton register_button, login_button,newGame_button,play_button;

    private void buildMenuTable(){
        final Table table = new Table();
        table.setFillParent(true);

        Label userNameLabel = new Label("UsernameExample", AssetManager.skin);
        userNameLabel.setPosition(Gdx.graphics.getWidth()*0.05f,Gdx.graphics.getHeight()*0.95f);


        login_button = new TextButton("Join", AssetManager.skin);
        login_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        login_button.setPosition(Gdx.graphics.getWidth()*0.15f,Gdx.graphics.getHeight()*0.95f);

        register_button = new TextButton("Register", AssetManager.skin);
        register_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        register_button.setPosition(Gdx.graphics.getWidth()*0.95f,Gdx.graphics.getHeight()*0.95f);

        newGame_button = new TextButton("New Game", AssetManager.skin);
        newGame_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        newGame_button.setPosition(Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight()*0.45f);

        play_button = new TextButton("PLAY", AssetManager.skin);
        play_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        play_button.setPosition(Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight()*0.5f);

        //afegir actorsº
        stage.addActor(userNameLabel);
        stage.addActor(login_button);
        stage.addActor(register_button);
        stage.addActor(play_button);
        stage.addActor(newGame_button);
        register_button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.setScreen(new RegisterScreen(Game));
            }
        } );

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        AssetManager.skin.dispose();
    }
}

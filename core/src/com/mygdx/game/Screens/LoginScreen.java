package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Message;
import com.mygdx.game.MyFolGame;
import com.mygdx.game.Settings.AssetManager;
import com.mygdx.game.Settings.UIFactory;

public class LoginScreen implements Screen {
    private MyFolGame game;
    private Stage stage;

    public LoginScreen(MyFolGame game) {
        this.game = game;

        Gdx.input.setInputProcessor(stage);
        Message.show("aaaaaaaaaaaaaaaa");
        stage = new UIFactory(game).getLoginMenu();

    }

    private TextField username_field,email_field,password_field,password_confirmation_field;
    private TextButton register_button,return_button;
    Actor bckgrndTouchCatcher;
    private void buildRegisterTable() {



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

        Message.stage.draw();
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

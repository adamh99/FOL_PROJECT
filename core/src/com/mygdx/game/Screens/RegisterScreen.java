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
import com.mygdx.game.MyFolGame;
import com.mygdx.game.Settings.AssetManager;

public class RegisterScreen implements Screen {
    private MyFolGame Game;
    private Stage stage;

    public RegisterScreen(MyFolGame game) {
        Game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        bckgrndTouchCatcher = new Actor();
        bckgrndTouchCatcher.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        bckgrndTouchCatcher.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.setKeyboardFocus(null);
            }
        } );


        buildRegisterTable();
    }

    private TextField username_field,email_field,password_field,password_confirmation_field;
    private TextButton register_button,return_button;
    Actor bckgrndTouchCatcher;
    private void buildRegisterTable() {

        register_button = new TextButton("Register", AssetManager.skin);
        register_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        register_button.setPosition(Gdx.graphics.getWidth()*0.5f-(register_button.getWidth()),Gdx.graphics.getHeight()*0.2f);

        password_confirmation_field = new TextField("",AssetManager.skin);
        password_confirmation_field.setMessageText("Confirm your password");
        password_confirmation_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        password_confirmation_field.setPosition(register_button.getX(), register_button.getY()+password_confirmation_field.getHeight());
        password_confirmation_field.setPasswordMode(true);
        password_confirmation_field.setPasswordCharacter('*');

        password_field = new TextField("",AssetManager.skin);
        password_field.setMessageText("Password");
        password_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        password_field.setPosition(register_button.getX(),password_confirmation_field.getY()+password_field.getHeight());
        password_field.setPasswordMode(true);
        password_field.setPasswordCharacter('*');


        email_field = new TextField("",AssetManager.skin);
        email_field.setMessageText("Email");
        email_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        email_field.setPosition(register_button.getX(),password_field.getY()+email_field.getHeight());

        username_field = new TextField("",AssetManager.skin);
        username_field.setMessageText("Username");
        username_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        username_field.setPosition(register_button.getX(), email_field.getY()+ username_field.getHeight());

        return_button = new TextButton("Home", AssetManager.skin);
        return_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        return_button.setPosition(Gdx.graphics.getWidth()*0.95f,Gdx.graphics.getHeight()*0.95f);

        //afegir actors
        stage.addActor(bckgrndTouchCatcher);
        stage.addActor(return_button);
        stage.addActor(register_button);
        stage.addActor(username_field);
        stage.addActor(email_field);
        stage.addActor(password_field);
        stage.addActor(password_confirmation_field);

        return_button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.setScreen(new TitleScreen(Game));
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

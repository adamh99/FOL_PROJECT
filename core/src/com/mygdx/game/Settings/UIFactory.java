package com.mygdx.game.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyFolGame;
import com.mygdx.game.Screens.TitleScreen;

import java.util.ArrayList;

public class UIFactory {
    MyFolGame game;
    public UIFactory(MyFolGame game){
        this.game = game;


    }
    private static Stage stage;
    ArrayList<TextField> textFields;
    ArrayList<TextButton> textButtons;
    float small_w = 0.2f;
    float medium_w = 0.4f;
    float large_w = 0.6f;

    float small_h = 0.1f;
    float medium_h = 0.2f;
    float large_h = 0.3f;

    public Stage getTitleMenu(){
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));


        return stage;
    }


    private TextField username_field,email_field,password_field,password_confirmation_field;
    private TextButton register_button,return_button;
    public Stage getRegisterMenu(){
        stage = new Stage();
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
        stage.addActor(return_button);
        stage.addActor(register_button);
        stage.addActor(username_field);
        stage.addActor(email_field);
        stage.addActor(password_field);
        stage.addActor(password_confirmation_field);

        return_button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TitleScreen(game));
            }
        } );
        return stage;
    }
    public Stage getInGameMenu() {

        return stage;
    }
}

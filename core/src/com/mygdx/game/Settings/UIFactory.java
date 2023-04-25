package com.mygdx.game.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Api.RetrofitInterface;
import com.mygdx.game.Message;
import com.mygdx.game.MyFolGame;
import com.mygdx.game.Screens.TitleScreen;




import java.util.ArrayList;
import java.util.HashMap;

import jdk.nashorn.internal.runtime.GlobalFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.java2d.opengl.WGLSurfaceData;

public class UIFactory {
    MyFolGame game;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL="http://localhost:3012";

    private String nomUsuari;

    private boolean logueado=false;
    private boolean registrat=false;

    public UIFactory(MyFolGame game){
        this.game = game;
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface=retrofit.create(RetrofitInterface.class);

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
    private TextButton register_button,login_button,return_button;
    private Actor bckgrndTouchCatcher;
    public Stage getRegisterMenu(){
        stage = new Stage();
        float center = Gdx.graphics.getWidth()*0.5f;
        Gdx.input.setInputProcessor(stage);
        bckgrndTouchCatcher = new Actor();
        bckgrndTouchCatcher.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        register_button = new TextButton("Register", AssetManager.skin);
        register_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        register_button.setPosition(center-(register_button.getWidth()/2),Gdx.graphics.getHeight()*0.2f);
        register_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle login button click
                String username = username_field.getText();
                String email = email_field.getText();
                String password = password_field.getText();
                // Do something with the email and password

                HashMap<String,String> map=new HashMap<>();

                map.put("name",username);
                map.put("email",email);
                map.put("password",password);

                Call<Void> call = retrofitInterface.executeSignup(map);
                System.out.println("llamando");
                call.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //okey

                        System.out.println("ha respondido "+response.code());

                        //Quan et logueges correctament
                        if (response.code()==201){
                            registrat=true;


                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run () {
                                    Message.show("Registrado correctamente");
                                }
                            });
                            //error
                        }  else if (response.code() == 452) {

                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run () {
                                    Message.show("mail regex fail");
                                    System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
                                }
                            });


                        } else if (response.code() == 453) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run () {
                                    Message.show("pass regex fail");
                                }
                            });

                        } else if (response.code() == 454) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run () {
                                    Message.show("user regex fail");
                                }
                            });

                        } else if (response.code() == 455) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run () {
                                    Message.show("username esta en uso");
                                }
                            });

                        } else if (response.code() == 456) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run () {
                                    Message.show("mail esta en uso");
                                }
                            });

                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });



            }
        });

        password_confirmation_field = new TextField("",AssetManager.skin);
        password_confirmation_field.setMessageText("Confirm your password");
        password_confirmation_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        password_confirmation_field.setPosition(center-(password_confirmation_field.getWidth()/2),register_button.getY()+password_confirmation_field.getHeight());
        password_confirmation_field.setPasswordMode(true);
        password_confirmation_field.setPasswordCharacter('*');

        password_field = new TextField("",AssetManager.skin);
        password_field.setMessageText("Password");
        password_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        password_field.setPosition(center-(password_field.getWidth()/2),password_confirmation_field.getY()+password_field.getHeight());
        password_field.setPasswordMode(true);
        password_field.setPasswordCharacter('*');

        email_field = new TextField("",AssetManager.skin);
        email_field.setMessageText("Email");
        email_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        email_field.setPosition(center-(email_field.getWidth()/2),password_field.getY()+email_field.getHeight());

        username_field = new TextField("",AssetManager.skin);
        username_field.setMessageText("Username");
        username_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        username_field.setPosition(center-(username_field.getWidth()/2), email_field.getY()+ username_field.getHeight());

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

        bckgrndTouchCatcher.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.setKeyboardFocus(null);
            }
        } );
        return_button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TitleScreen(game));
            }
        } );
        return stage;
    }
    public Stage getLoginMenu(){
        stage = new Stage();
        //mitad de pantalla
        float center = Gdx.graphics.getWidth()*0.5f;
        Gdx.input.setInputProcessor(stage);
        bckgrndTouchCatcher = new Actor();
        bckgrndTouchCatcher.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        login_button = new TextButton("Login", AssetManager.skin);
        login_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        login_button.setPosition(center-(login_button.getWidth()/2),Gdx.graphics.getHeight()*0.2f);
        login_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle login button click
                String username = username_field.getText();
                String password = password_field.getText();
                // Do something with the email and password

                HashMap<String,String> map=new HashMap<>();

                map.put("name",username);
                map.put("password",password);

                Call<Void> call = retrofitInterface.executeLogin(map);
                System.out.println("llamando");
                call.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //okey
                        System.out.println("ha respondido");
                        System.out.println(response.code());
                        //Quan et logueges correctament
                        if (response.code()==202){
                            logueado=true;
                            username_field.setColor(Color.RED);
                            //error
                        }  else if (response.code() == 401) {
                            username_field.setColor(Color.RED);

                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });



            }
        });


        password_field = new TextField("",AssetManager.skin);
        password_field.setMessageText("Password");
        password_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        password_field.setPosition(center-(password_field.getWidth()/2),login_button.getY()+password_field.getHeight());
        password_field.setPasswordMode(true);
        password_field.setPasswordCharacter('*');

        email_field = new TextField("",AssetManager.skin);
        email_field.setMessageText("Email");
        email_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        email_field.setPosition(center-(email_field.getWidth()/2),password_field.getY()+email_field.getHeight());

        username_field = new TextField("",AssetManager.skin);
        username_field.setMessageText("Username");
        username_field.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.025f);
        username_field.setPosition(center-(username_field.getWidth()/2), email_field.getY()+ username_field.getHeight());

        return_button = new TextButton("Home", AssetManager.skin);
        return_button.setSize(Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getWidth()*0.025f);
        return_button.setPosition(Gdx.graphics.getWidth()*0.95f,Gdx.graphics.getHeight()*0.95f);


        stage.addActor(bckgrndTouchCatcher);
        stage.addActor(return_button);
        stage.addActor(login_button);
        stage.addActor(password_field);
        stage.addActor(email_field);
        stage.addActor(username_field);

        bckgrndTouchCatcher.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.setKeyboardFocus(null);
            }
        } );
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

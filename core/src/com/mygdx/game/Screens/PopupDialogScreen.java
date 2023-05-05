package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Settings.AssetLoader;

public class PopupDialogScreen implements Screen {

    private Stage stage;
    private Skin skin = AssetLoader.skin;
    private String message;
    private TextButton closeButton;

    Screen underlying;

    public PopupDialogScreen(String title, String message, Screen underlying) {
    enum Positions{
        TOP_RIGHT,TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT
    }
    public PopupDialogScreen(String message, Skin skin, Screen underlying, Positions screenPos) {
        this.message = message;
        this.underlying = underlying;
        stage = new Stage(new ScreenViewport());
        // create and add the dialog to the stage
        Dialog dialog = new Dialog(title, skin);
        dialog.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()*0.1f);
        dialog.setSize(300, 300);
        //dialog.show(stage);
        Label messageLabel = new Label(message, skin);
        dialog.add(messageLabel).pad(10);

        closeButton = new TextButton("Close", skin);
        dialog.button(closeButton, true);
        stage.addActor(dialog);
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
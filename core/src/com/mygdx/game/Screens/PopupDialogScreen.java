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

public class PopupDialogScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private String message;
    private TextButton closeButton;

    Screen underlying;

    public PopupDialogScreen(String message, Skin skin, Screen underlying) {
        this.message = message;
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        System.out.println("POP UP DIALOG CONSTRUCTOR");
    }

    @Override
    public void show() {
        // create and add the dialog to the stage
        Dialog dialog = new Dialog("NPC Dialog", skin);
        dialog.setPosition(100, 100);
        dialog.setSize(200, 100);

        Label messageLabel = new Label(message, skin);
        dialog.add(messageLabel).pad(10);

        closeButton = new TextButton("Close", skin);
        dialog.button(closeButton, true);
        stage.addActor(dialog);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        underlying.render(delta);
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
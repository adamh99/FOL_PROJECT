package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Settings.AssetLoader;


public class Message {
    private static final float DURATION = 2.0f; // Display duration in seconds
    private static final Skin skin = AssetLoader.skin;
    public static final Stage stage = new Stage();
    private static final Label label = new Label("", skin);

    static {
        // Set up the label
        label.setAlignment(Align.center);
        label.setPosition(00, Gdx.graphics.getHeight() / 2.0f, Align.center);
        label.setWidth(Gdx.graphics.getWidth());
        stage.addActor(label);
    }

    public static void show(String message) {
        label.setText(message);
        label.setColor(Color.WHITE);

        // Display the label
        stage.draw();

        // Hide the label after a delay
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                label.setColor(Color.CLEAR);
                stage.draw();
            }
        }, DURATION);
    }

    public static void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
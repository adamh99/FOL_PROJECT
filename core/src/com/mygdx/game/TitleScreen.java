package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TitleScreen implements Screen {
    MyFolGame game;
    private Texture background;
    private SpriteBatch batch;

    public TitleScreen(MyFolGame game) {
        background = new Texture("badlogic.jpg");
        batch = new SpriteBatch();
        this.game = game;
        game.setScreen(this);
    }

    @Override
    public void show() {
        System.out.println("showing screen");
    }

    @Override
    public void render(float delta) {

        // Clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float textureWidth = background.getWidth();
        float textureHeight = background.getHeight();
        float scaleX = screenWidth / textureWidth;
        float scaleY = screenHeight / textureHeight;
        float scale = Math.min(scaleX, scaleY);

        System.out.println("Screen size: " + screenWidth + "x" + screenHeight);
        System.out.println("Texture size: " + textureWidth + "x" + textureHeight);
        System.out.println("Scale factor: " + scale);

        batch.begin();
        batch.draw(background, 0, 0, textureWidth * scale, textureHeight * scale);
        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        // Called when the screen size is changed.
    }

    @Override
    public void pause() {
        // Called when the game is paused.
    }

    @Override
    public void resume() {
        // Called when the game is resumed from a paused state.
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen.
    }

    @Override
    public void dispose() {
        // Dispose of any resources used by this screen.
        background.dispose();
        batch.dispose();
    }
}

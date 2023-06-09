package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Sidebar extends Actor {
    private Sprite backgroundSprite;
    private BitmapFont font;
    private boolean expanded;
    private SpriteBatch batch;

    private Color backgroundcolor;

    public Sidebar(Texture background, BitmapFont font, String[] options, float x, float y, float width, float height, SpriteBatch batch) {
        this.font = font;
        this.batch = batch;

        backgroundSprite = new Sprite(background);
        backgroundSprite.setPosition(Gdx.graphics.getWidth() - width, y);
        backgroundSprite.setSize(width, height);
        // Cambiar la posición y tamaño del sprite de fondo
        backgroundSprite.setPosition(x, y);
        backgroundSprite.setSize(width, height);

        // No es necesario llamar a batch.begin() aquí

        // Cambiar la posición del contenido
        float contentX = expanded ? Gdx.graphics.getWidth() - width + 10 : Gdx.graphics.getWidth() - width + 10;
        float contentY = y + height - 10;

        // Cambiar el tamaño del contenido
        float contentWidth = expanded ? width - 20 : width - 30;
        float contentHeight = height - 20;
        // Llamar a batch.begin() antes de dibujar
        batch.begin();
        font.draw(batch, "Sidebar Content", contentX, contentY, contentWidth, (int) contentHeight, true);
        batch.end();
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        backgroundSprite.draw(batch);

        // Draw the content
        float contentX = expanded ? getX() + 10 : getX() + 10;
        float contentY = getY() + getHeight() - 10;

        batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE);

        font.draw(batch, "Sidebar Content", contentX, contentY);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

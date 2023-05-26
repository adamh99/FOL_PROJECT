package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Settings.AssetLoader;

public class ExperienceBar extends Actor {

    private Sprite backgroundSprite;
    private Sprite fillSprite;
    private float maxWidth;
    private float maxHeight;
    private float currentWidth;


    public ExperienceBar(float x,float y, float width, float height) {
  // Ajusta esto a la textura de relleno deseada

        backgroundSprite = new Sprite();
        fillSprite = new Sprite();


        setPosition(x, y);
        maxWidth = width;
        maxHeight = height;
        currentWidth = 0;
    }
    public void setExperiencePoints(int points) {
        float progress = (float) points / 100; // Ajusta esto según la lógica de tus puntos de experiencia
        currentWidth = maxWidth * progress;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(Color.WHITE);
        backgroundSprite.setBounds(getX(), getY(), maxWidth, maxHeight);
        backgroundSprite.draw(batch);

        batch.setColor(Color.GREEN);
        fillSprite.setBounds(getX(), getY(), currentWidth, maxHeight);
        fillSprite.draw(batch);
    }
}
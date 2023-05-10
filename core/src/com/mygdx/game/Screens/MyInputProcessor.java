package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.mygdx.game.GameScreen;

public class MyInputProcessor implements InputProcessor {

    private final PerspectiveCamera camera;
    private float startX, startY;
    private boolean isDragging;
    GameScreen gameScreen;

    public MyInputProcessor(PerspectiveCamera camera, GameScreen gameScreen) {
        this.camera = camera;
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        startX = screenX;
        startY = screenY;
        lastScreenX = screenX;
        lastScreenY = screenY;
        isDragging = true;
        heightIncreased = false;
        rotating = false;

        return true;
    }

    float lastScreenX;
    float lastScreenY;
    boolean heightIncreased = false;
    boolean rotating = false;
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!isDragging) return false;
        float deltaX = -(screenX+1 - lastScreenX);
        float deltaY = (screenY - lastScreenY);
        if(Math.abs(deltaX)>Math.abs(deltaY)) {
            gameScreen.rotateCam(deltaX);
            rotating = true;
            heightIncreased = true;

        } else{
            if (deltaY > Gdx.graphics.getHeight() / 12 && !heightIncreased) {
                gameScreen.increaseCamFloorLevel();
                heightIncreased = true;
            } else if (deltaY < -Gdx.graphics.getHeight() / 12 && !heightIncreased) {
                gameScreen.decreaseCamFloorLevel();
                heightIncreased = true;
            }

        }
        lastScreenX = screenX;
        lastScreenY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragging = false;
        return true;
    }



}



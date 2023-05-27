package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.GameScreen;

public class MyInputProcessor implements InputProcessor {

    private final PerspectiveCamera camera;
    private float startX, startY;
    private boolean isDragging;
    GameScreen gameScreen;
    Image menuButton;

    public MyInputProcessor(PerspectiveCamera camera, GameScreen gameScreen) {
        this.camera = camera;
        this.gameScreen = gameScreen;
        menuButton = gameScreen.menuButton;
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
        float deltaY = (screenY+1 - lastScreenY);

            gameScreen.rotateCam(deltaX);
            rotating = true;
            heightIncreased = true;


            gameScreen.setCamHeight(deltaY);



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
        if(isMenuButtonClicked(screenX,screenY)){
            if(gameScreen.isMenuVisible){
                gameScreen.slidePanelOut();
            }else gameScreen.slidePanelIn();
        }



        return true;
    }

    boolean isMenuButtonClicked(float screenX, float screenY){
        boolean clicked = false;
        if (screenX >= menuButton.getX() && screenX <= menuButton.getX() + menuButton.getWidth() &&
                Gdx.graphics.getHeight() - screenY >= menuButton.getY() && Gdx.graphics.getHeight() - screenY <= menuButton.getY() + menuButton.getHeight()) {
            clicked = true;
        }
        return clicked;
    }


}



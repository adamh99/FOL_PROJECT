package com.mygdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntIntMap;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

public class CamControl implements InputProcessor {
	private final Camera camera;
	private final IntIntMap keys = new IntIntMap();
	private int STRAFE_LEFT = Keys.A;
	private int STRAFE_RIGHT = Keys.D;
	private int FORWARD = Keys.W;
	private int BACKWARD = Keys.S;
	private int UP = Keys.Q;
	private int DOWN = Keys.E;
	private int SHIFT = Keys.SHIFT_LEFT;
	private int NEXT_MODEL = Keys.N;
	private int PLACE_MODEL = Keys.ENTER;
	private int MODEL_FORWARD = Keys.I;
	private int MODEL_LEFT = Keys.J;
	private int MODEL_RIGHT = Keys.L;
	private int MODEL_BACK = Keys.K;
	private int MODEL_UP = Keys.O;
	private int MODEL_DOWN =Keys.U;

	private float velocity = 5;
	private float degreesPerPixel = 0.5f;
	private final Vector3 tmp = new Vector3();

	int deltaX, deltaY;
	final float camRotSpeedX = 0.01f;
	final float camRotSpeedY = 0.01f;
	final float screenWidth, screenHeight;
	final int screenCenterX, screenCenterY;
	Robot bot;

	public CamControl(Camera camera) throws AWTException {
		this.camera = camera;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		screenCenterX = (int) (screenWidth / 2);
		screenCenterY = (int) (screenHeight / 2);
		if(Gdx.app.getType()==ApplicationType.Desktop) bot = new Robot();
	}

	@Override
	public boolean keyDown(int keycode) {
		keys.put(keycode, keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		keys.remove(keycode, 0);
		return true;
	}

	/**
	 * Sets the velocity in units per second for moving forward, backward and
	 * strafing left/right.
	 * 
	 * @param velocity the velocity in units per second
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	/**
	 * Sets how many degrees to rotate per pixel the mouse moved.
	 * 
	 * @param degreesPerPixel
	 */
	public void setDegreesPerPixel(float degreesPerPixel) {
		this.degreesPerPixel = degreesPerPixel;
	}

	int touchX  ;
	int touchY ;
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int magX = Math.abs(touchX - screenX);
		int magY = Math.abs(touchY - screenY);

		if (touchX > screenX) {
			camera.rotate(Vector3.Y, 1 * magX * rotSpeed);
			camera.update();
		} else if (touchX < screenX) {
			camera.rotate(Vector3.Y, -1 * magX * rotSpeed);
			camera.update();
		}

		if (touchY < screenY) {
			if (camera.direction.y > -0.965)
				camera.rotate(camera.direction.cpy().crs(Vector3.Y), -1 * magY * rotSpeed);
			camera.update();
		} else if (touchY > screenY) {

			if (camera.direction.y < 0.965)
				camera.rotate(camera.direction.cpy().crs(Vector3.Y), 1 * magY * rotSpeed);
			camera.update();
		}

		/*
		 * mouseX is set to the center of the screen so it has room to do 360 rotation
		 * mouseY can't do 360� rotation or the camera would be upside down
		 */
		touchX = screenX;
		touchY = screenY;
		return false;
		//
		
	}

	public void update() throws IOException {
		update(Gdx.graphics.getDeltaTime());
	}

	public void update(float deltaTime) throws IOException {

		if (keys.containsKey(FORWARD)) {
			tmp.set(camera.direction).nor().scl(deltaTime * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(BACKWARD)) {
			tmp.set(camera.direction).nor().scl(-deltaTime * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(STRAFE_LEFT)) {
			tmp.set(camera.direction).crs(camera.up).nor().scl(-deltaTime * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(STRAFE_RIGHT)) {
			tmp.set(camera.direction).crs(camera.up).nor().scl(deltaTime * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(UP)) {
			tmp.set(camera.up).nor().scl(deltaTime * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(DOWN)) {
			tmp.set(camera.up).nor().scl(-deltaTime * velocity);
			camera.position.add(tmp);
		}
		if (keys.containsKey(SHIFT)) {
			camera.position.y += deltaTime;
		}
		if (keys.containsKey(NEXT_MODEL)) {
			MapBuilder.next_model();
			keys.remove(NEXT_MODEL, 0);
		}
		if (keys.containsKey(PLACE_MODEL)) {
			MapBuilder.place_model();
			keys.remove(PLACE_MODEL, 0);
		}
		if (keys.containsKey(MODEL_LEFT)) {
			MapBuilder.incrementPosition(2, 0, 0);
			keys.remove(Keys.J, 0);
		}
		if (keys.containsKey(MODEL_RIGHT)) {
			MapBuilder.incrementPosition(-2, 0, 0);
			keys.remove(Keys.L, 0);
		}
		if (keys.containsKey(MODEL_FORWARD)) {
			MapBuilder.incrementPosition(0, 0, 2);
			keys.remove(Keys.I, 0);
		}
		if (keys.containsKey(MODEL_BACK)) {
			MapBuilder.incrementPosition(0, 0, -2);
			keys.remove(Keys.K, 0);
		}
		if (keys.containsKey(MODEL_UP)) {
			MapBuilder.incrementPosition(0, 1, 0);
			keys.remove(Keys.O, 0);
		} if (keys.containsKey(MODEL_DOWN)) {
			MapBuilder.incrementPosition(0, -1, 0);
			keys.remove(Keys.U, 0);
		}
	if (keys.containsKey(Keys.R)) {
		MapBuilder.rotateClockwise();;
		keys.remove(Keys.R, 0);
	}
		if (keys.containsKey(Keys.BACKSPACE)) {
			keys.remove(Keys.BACKSPACE, 0);
			MapBuilder.deleteLast();
		}
		if (keys.containsKey(Keys.NUM_0)) {
			keys.remove(Keys.NUM_0, 0);
			MapBuilder.printMapJson();
		}
		if (keys.containsKey(Keys.C)) {
			keys.remove(Keys.C, 0);
			MapBuilder.next_category();
		}

		camera.update(true);
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	static boolean android_forward = false;
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchX = screenX;
		touchY = screenY;
		android_forward = true;

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		android_forward = false;
		return false;
	}

	private int mouseX = 0;
	private int mouseY = 0;
	private float rotSpeed = 0.2f;

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		int magX = Math.abs(mouseX - screenX);
		int magY = Math.abs(mouseY - screenY);

		if (mouseX > screenX) {
			camera.rotate(Vector3.Y, 1 * magX * rotSpeed);
			camera.update();
		} else if (mouseX < screenX) {
			camera.rotate(Vector3.Y, -1 * magX * rotSpeed);
			camera.update();
		}

		if (mouseY < screenY) {
			if (camera.direction.y > -0.965)
				camera.rotate(camera.direction.cpy().crs(Vector3.Y), -1 * magY * rotSpeed);
			camera.update();
		} else if (mouseY > screenY) {

			if (camera.direction.y < 0.965)
				camera.rotate(camera.direction.cpy().crs(Vector3.Y), 1 * magY * rotSpeed);
			camera.update();
		}

		/*
		 * mouseX is set to the center of the screen so it has room to do 360 rotation
		 * mouseY can't do 360� rotation or the camera would be upside down
		 */
		mouseX = screenCenterX;
		mouseY = screenY;
		bot.mouseMove(screenCenterX, screenY);

		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
}
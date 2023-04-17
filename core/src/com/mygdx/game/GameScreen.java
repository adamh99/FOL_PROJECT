package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.AWTException;
import java.io.IOException;

public class GameScreen implements Screen {
	//features a test 3d world
	ModelBatch modelBatch;
	public PerspectiveCamera cam;
	
	public Model model;
	public Model modelTest;
	static Array<MapObject> instances;
	
	
	public Environment environment;
	
	CamControl camController;
	public float delta;
	
	//DEBUG UI
	Stage debugUi;
	BitmapFont debugFont;
	SpriteBatch sb;
	
	ModelBuilder modelBuilder;//debugCursor
	MapBuilder mapBuilder;
	boolean loading;
	
	ModelInstance debug3dcursor;
	

	public GameScreen(){
		modelBatch = new ModelBatch();
		instances = new Array<MapObject>();
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(1f, 10f, 1f);
		cam.lookAt(0,0,0);
		cam.near = 0.15f;
		cam.far = 300f;
		cam.update();
		
		loading = true;
		//controlls
		try {
			camController = new CamControl(cam);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Gdx.input.setInputProcessor(camController);
		Gdx.input.setCursorCatched(false);
		
		//ui
		debugUi = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		debugFont = new BitmapFont(Gdx.files.internal("default.fnt"));

		debugFont.setColor(1,0,0, 1);
		sb = new SpriteBatch ();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.01f, 0.01f, 0.01f, 1f));
		
		environment.add(new PointLight().set(1f,0.6f,0.1f,-3f,8f,9f,70f));
		
		
		MapBuilder.init(); //loads assets
		
		try {
			MapBuilder.loadMapFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		 modelBuilder = new ModelBuilder();
		 
		loadInstances();
		
    }
	
		
	public void loadInstances() {
	
		/*modelIn.materials.get(0).set(
			    new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
			);
		*/ //parece que no hace nada?
		
		

		loading = false;
	}
	public void updateEntityPos() {
		
	}

	Vector3 tmp = new Vector3();
	@Override
	public void render (float delta) {
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
		
		//color azul para el cielo
		Gdx.gl.glClearColor(150f, 10f, 2f,0.5f);
		if(CamControl.android_forward){tmp.set(cam.direction).nor().scl(delta * 1);
		cam.position.add(tmp);}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | 
				(Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

		if (loading && MapBuilder.assets.update())
			loadInstances();
		try {
			camController.update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		modelBatch.begin(cam);
		modelBatch.render(instances,environment);
		
		modelBatch.end();
		
		sb.begin();
		debugFont.draw(sb, "FPS="+Gdx.graphics.getFramesPerSecond()+" camxyz="
		+cam.position, camController.screenWidth/2, (float) (camController.screenHeight*0.97));
		sb.end();

	}

	@Override
	public void show() {

	}



	@Override
	public void resize(int width, int height) {

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
	public void dispose () {
		modelBatch.dispose();
		
	}
}

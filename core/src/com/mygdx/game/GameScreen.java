package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
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
	static Array<ModelInstance> instances;
	Model firstFloor,interiorfirstFloor,middleFloor,interiorMiddleFloor,topFloor,interiorTopFloor;
	final float SCALE = 0.125f;
	public Environment environment;
	
	CamControl camController;
	public float delta;
	
	//DEBUG UI

	BitmapFont debugFont;
	SpriteBatch sb;

	boolean loading;
	
	ModelInstance debug3dcursor;
	

	public GameScreen(){
		modelBatch = new ModelBatch();
		instances = new Array<ModelInstance>();
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(1f, 10f, 1f);
		cam.lookAt(0,0,0);
		cam.near = 0.15f;
		cam.far = 100000000000f;
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

		debugFont = new BitmapFont(Gdx.files.internal("default.fnt"));

		debugFont.setColor(1,0,0, 1);
		sb = new SpriteBatch ();

		setLighting();
    }
	private void setLighting(){
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

		// Define the direction of the light
		Vector3 direction = new Vector3(-1f, -0.5f, -1f).nor();

// Define the color of the light
		Color color = new Color(1f, 1f, 0.8f, 1f);

// Create the DirectionalLight object
		DirectionalLight light = new DirectionalLight();
		light.set(color, direction);

// Add the light to the environment
		environment.add(light);

	}
	AssetManager assets = new AssetManager();
	public void loadInstances() {


		assets.load("floors/FirstFloor.g3db",Model.class);
		assets.load("floors/InteriorFirstFloor.g3db",Model.class);
		assets.load("floors/MiddleFloor.g3db",Model.class);
		assets.load("floors/InteriorMiddleFloor.g3db",Model.class);
		assets.load("floors/InteriorTopFloor.g3db",Model.class);
		assets.load("floors/TopFloor.g3db",Model.class);
		assets.finishLoading();

		firstFloor = assets.<Model>get("floors/FirstFloor.g3db");
		interiorfirstFloor = assets.<Model>get("floors/InteriorFirstFloor.g3db");
		middleFloor = assets.<Model>get("floors/MiddleFloor.g3db");
		interiorMiddleFloor = assets.<Model>get("floors/InteriorMiddleFloor.g3db");
		topFloor = assets.<Model>get("floors/TopFloor.g3db");
		interiorTopFloor = assets.<Model>get("floors/InteriorTopFloor.g3db");

		/*ModelInstance firstFloorIn = new ModelInstance(firstFloor);
		ModelInstance interiorFirstFloorIn = new ModelInstance(interiorfirstFloor);
		ModelInstance middleFloorIn = new ModelInstance(middleFloor);
		ModelInstance topFloorIn = new ModelInstance(topFloor);


		interiorFirstFloorIn.transform = new Matrix4(new Vector3(0,0,0),new Quaternion(),new Vector3(0.125f,0.125f,0.125f));
		firstFloorIn.transform = new Matrix4(new Vector3(0,0,0),new Quaternion(),new Vector3(0.125f,0.125f,0.125f));
		middleFloorIn.transform = new Matrix4(new Vector3(0,getFloorHeight(),getFloorDepth()),new Quaternion(),new Vector3(0.125f,0.125f,0.125f));
		topFloorIn.transform = new Matrix4(new Vector3(0,0,0),new Quaternion(),new Vector3(0.125f,0.125f,0.125f));


		instances.add(firstFloorIn);
		instances.add(interiorFirstFloorIn);
		instances.add(middleFloorIn);
		instances.add(topFloorIn);*/
		Array<ModelInstance> floors = new Array<>();

		for (int i = 0; i < 10; i++){
			ModelInstance aux = null;
			ModelInstance aux2 = null;
			if(i==0){
				aux = new ModelInstance(firstFloor);
				aux.transform = new Matrix4(new Vector3(0,0,0),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));

				aux2 = new ModelInstance(interiorfirstFloor);
				aux2.transform = new Matrix4(new Vector3(0,0,0),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));

				floors.add(aux);
				floors.add(aux2);
			}else if(i<9){
				aux = new ModelInstance(middleFloor);
				aux.transform = new Matrix4(new Vector3(0,getFloorHeight()*i,0),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));

				aux2 = new ModelInstance(interiorMiddleFloor);
				aux2.transform = new Matrix4(new Vector3(0,getFloorHeight()*i,0),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));
				floors.add(aux);
				floors.add(aux2);
			}else if(i<10){
				aux = new ModelInstance(topFloor);
				aux.transform = new Matrix4(new Vector3(0,getFloorHeight()*i,0),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));

				aux2 = new ModelInstance(interiorTopFloor);
				aux2.transform = new Matrix4(new Vector3(0,getFloorHeight()*i,0),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));
				floors.add(aux);
				floors.add(aux2);
			}
			instances.add(aux);
			instances.add(aux2);
		}



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

		if (loading && assets.update())
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

	public float getFloorHeight(){
		float unscaled_h = middleFloor.meshes.first().calculateBoundingBox().getHeight();
		float actual_h = unscaled_h/2+unscaled_h/SCALE*1.5f; //el 1.5 y el 2 han salido de ensayo y error, no se sabe q logica tiene
	return actual_h;
	}
	public float getFloorWidth(){
		float unscaled_w = middleFloor.meshes.first().calculateBoundingBox().getWidth();
		float actual_w = unscaled_w/2+unscaled_w/SCALE*1.5f;
		return actual_w;
	}
	public float getFloorDepth(){
		float unscaled_d = middleFloor.meshes.first().calculateBoundingBox().getDepth();
		float actual_d = unscaled_d/2+unscaled_d/SCALE*1.5f;
		return actual_d;
	}
}

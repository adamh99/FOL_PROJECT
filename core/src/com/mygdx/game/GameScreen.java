package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Api.RetrofitInterface;
import com.mygdx.game.Screens.MyInputProcessor;
import com.mygdx.game.Screens.PopupDialogScreen;


import java.awt.AWTException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameScreen implements Screen {

	public long startTime;

	BitmapFont font;
	//features a test 3d world
	ModelBatch modelBatch;

	private String userName;

	Stage stage;
	public PerspectiveCamera cam;
	public MyInputProcessor myInputProcessor;
	static Array<ModelInstance> instances;
	Model firstFloor,interiorfirstFloor,middleFloor,interiorMiddleFloor,topFloor,interiorTopFloor,furniture;
	final float SCALE = 0.125f;
	public Environment environment;

	public CamControl camController;
	public float delta;
	private Question[] questions;
	//DEBUG UI
	BitmapFont debugFont;
	SpriteBatch sb;

	boolean loading;
	ModelInstance debug3dcursor;
	public QuizManager qmanager;
	private BitmapFont fonti; // Fuente para el texto
	private SpriteBatch batch;
	MyFolGame game;
	public Stack<Question> currentQuiz;

	public GameScreen(MyFolGame game, QuizManager quizManager) throws IOException, InterruptedException {
		font = new BitmapFont();
		startTime = System.currentTimeMillis();
		currentQuiz = new Stack<>();
		modelBatch = new ModelBatch();
		instances = new Array<ModelInstance>();
		this.game = game;
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		myInputProcessor = new MyInputProcessor(cam, this);
		fonti = new BitmapFont();

		batch= new SpriteBatch();

		qmanager=quizManager;
		questions = qmanager.fetchQuestionsFromServer();
		stage=new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(myInputProcessor);
		qmanager.quizzScreen(questions,this,stage);
		//qmanager.startQuiz(questions,this,stage);


		//displayQuestionDialog(questions, PopupDialogScreen.EnumClass.Positions.CENTER);
		cam.position.set(1f, 10f, 1f);
		cam.lookAt(0,0,0);
		cam.near = 0.15f;
		cam.far = 1000f;
		cam.update();

		loading = true;
		//controlls
		try {
			camController = new CamControl(cam);
		} catch (AWTException e) {
			e.printStackTrace();
		}

		Gdx.input.setCursorCatched(false);

		//ui

		debugFont = new BitmapFont(Gdx.files.internal("default.fnt"));

		debugFont.setColor(1,0,0, 1);
		sb = new SpriteBatch ();

		setLighting();
    }

	private void setUserName(String userName){
		this.userName=userName;

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
		assets.load("floors/funciona.g3db",Model.class);
		assets.finishLoading();

		firstFloor = assets.<Model>get("floors/FirstFloor.g3db");
		interiorfirstFloor = assets.<Model>get("floors/InteriorFirstFloor.g3db");
		middleFloor = assets.<Model>get("floors/MiddleFloor.g3db");
		interiorMiddleFloor = assets.<Model>get("floors/InteriorMiddleFloor.g3db");
		topFloor = assets.<Model>get("floors/TopFloor.g3db");
		interiorTopFloor = assets.<Model>get("floors/InteriorTopFloor.g3db");
		furniture = assets.<Model>get("floors/funciona.g3db");

		ModelInstance aux = null;
		ModelInstance aux2 = null;
		ModelInstance aux3 = null;
		Array<ModelInstance> floors = new Array<>();

		Color[] colors = new Color[] {
				new Color(0.69f, 0.48f, 0.38f, 1),  // Earthy brown
				new Color(0.9f, 0.9f, 0.9f, 1),    // Light gray
				new Color(0.63f, 0.77f, 0.8f, 1),  // Light blue
				new Color(0.58f, 0.73f, 0.43f, 1), // Olive green
				new Color(0.9f, 0.63f, 0.56f, 1),  // Coral
				new Color(0.47f, 0.45f, 0.48f, 1)  // Charcoal gray
		};
		int colorIterator = 0;


		for(int j = 0; j < 1; j++){
			float random_w = 0;//(float) Math.random()*getFloorWidth();
			float random_d = 0;//(float) Math.random()*getFloorDepth();

			int floorNumber = 6;

		for (int i = 0; i < floorNumber; i++){


			float random = (float)Math.random()*getFloorWidth();
			if(i==0){
				aux = new ModelInstance(firstFloor);
				aux.transform = new Matrix4(new Vector3(0,0,0),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));

				aux2 = new ModelInstance(interiorfirstFloor);
				aux2.transform = new Matrix4(new Vector3(random_w*j,0,random_d*j),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));


				floors.add(aux);
				floors.add(aux2);
			}else if(i<(floorNumber-1)){
				aux = new ModelInstance(middleFloor);
				aux.transform = new Matrix4(new Vector3(random_w*j,getFloorHeight()*i,random_d*j),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));

				aux2 = new ModelInstance(interiorMiddleFloor);
				aux2.transform = new Matrix4(new Vector3(random_w*j,getFloorHeight()*i,random_d*j),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));
				aux3 = new ModelInstance(furniture);
				aux3.transform = new Matrix4(new Vector3(random_w*j,getFloorHeight()*i+0.25f,random_d*j),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));

				if(colorIterator>=colors.length)colorIterator = 0;

				aux.materials.pop().set(new ColorAttribute(ColorAttribute.Diffuse, colors[colorIterator]));
				colorIterator++;

				floors.add(aux);
				floors.add(aux2);
				instances.add(aux3);
			}else if(i<floorNumber){
				aux = new ModelInstance(topFloor);
				aux.transform = new Matrix4(new Vector3(random_w*j,getFloorHeight()*i,random_d*j),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));
				aux2 = new ModelInstance(interiorTopFloor);
				aux2.transform = new Matrix4(new Vector3(random_w*j,getFloorHeight()*i,random_d*j),new Quaternion(),new Vector3(SCALE,SCALE,SCALE));

				floors.add(aux);
				floors.add(aux2);
			}
			instances.add(aux);
			instances.add(aux2);

		}
		}



		loading = false;
	}

	public void saveGameTime(int gameTime,String userName){
		Retrofit retrofit;
		RetrofitInterface retrofitInterface;
		String BASE_URL="http://localhost:3012";

		retrofit=new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		retrofitInterface=retrofit.create(RetrofitInterface.class);

		HashMap<String,String> map=new HashMap<>();




		map.put("gameTime",String.valueOf(gameTime));
		map.put("userName",userName);

		Call<Void> call = retrofitInterface.executeSaveStats(map);

		call.enqueue(new Callback<Void>() {
			@Override
			public void onResponse(Call<Void> call, Response<Void> response) {

			}

			@Override
			public void onFailure(Call<Void> call, Throwable t) {

			}
		});


	}

	public boolean popUp = false;

	public PopupDialogScreen currentPopUp = null;
	/*public void displayPopUpDialog(String title, String message, PopupDialogScreen.EnumClass.Positions positions, ){
		popupscreen = new PopupDialogScreen(title,message,this,positions.CENTER,stage);
		popUp = true;
	}*/



	Vector3 tmp = new Vector3();

	@Override
	public void render (float delta) {
		this.delta = delta;
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
		int totalPoints = qmanager.getTotalPoints();
		int score = qmanager.getScore();
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		GlyphLayout scoreLayout = new GlyphLayout(fonti, "Puntuación: " + score);
		float scoreX = screenWidth / 2 - scoreLayout.width / 2;
		float scoreY = 50;

		GlyphLayout levelLayout = new GlyphLayout(fonti, "Nivel: " + totalPoints);
		float levelX = screenWidth / 2 - levelLayout.width / 2;
		float levelY = 30;

		modelBatch.end();

		sb.begin();
		debugFont.draw(sb, "FPS="+Gdx.graphics.getFramesPerSecond()+" camxyz="
		+cam.position, camController.screenWidth/2, (float) (camController.screenHeight*0.97));
		sb.end();

		batch.begin();
		font.draw(batch, "Puntos: " + score, scoreX, scoreY); // Reemplaza "x" e "y" con las coordenadas de posición deseadas
		font.draw(batch, "Puntuación total: " + totalPoints, levelX, levelY); // Reemplaza "x" e "y" con las coordenadas de posición deseadas
		batch.end();

		if(popUp){
			currentPopUp.render(delta);


		}
		if(!loading){

			updateTreeCamera();
		}


	}
	private String calculateLevel(int score) {

		if (score < 10) {
			return "Novato";
		} else if (score < 20) {
			return "Intermedio";
		} else {
			return "Experto";
		}
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
		batch.dispose();
		font.dispose();
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

	static final float CAM_PATH_RADIUS = 100f;
	static float cam_height = 8f;
	static float target_height = 8f;
	float camPathAngle = 0;

	void updateTreeCamera(){
		Vector3 camPosition = cam.position;
		camPosition.set(CAM_PATH_RADIUS, cam_height, 0); //Move camera to default location on circle centered at origin
		camPosition.rotate(Vector3.Y, camPathAngle); //Rotate the position to the angle you want. Rotating this vector about the Y axis is like walking along the circle in a counter-clockwise direction.
		camPosition.add(new Vector3(getFloorWidth()/2,0,getFloorWidth()/2)); //translate the circle from origin to tree center
		cam.up.set(Vector3.Y); //Make sure camera is still upright, in case a previous calculation caused it to roll or pitch
		cam.lookAt(new Vector3(getFloorWidth()/2,cam_height,getFloorWidth()/2));
		cam.update(); //Register the changes to the camera position and direction


	}

	public void rotateCam(float magnitude){
		camPathAngle += magnitude/10;
	}
	public void setCamHeight(float height){
	cam_height += height/8;
	}

}

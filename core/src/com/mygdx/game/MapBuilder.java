package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class MapBuilder {
	
	
	static AssetManager assets;
	static Model selectedModel;
	static Matrix4 currentTransform;
	static final String[] categories = {"floors","walls"};
	static String selectedCategory;
	static String selectedFile;
	static int fileIterator;
	static int categoryIterator;
	static MapObject placedModel;
	static Stack<MapObject> history;
	static ArrayList<MapObject> floors;
	static String[] floorFiles = {"cobble_floor.g3db"};
	static String floorPath = "floors/";
	
	static String[] wallFiles = {"dungeon_wall01.g3db"};
	static String wallPath = "walls/";
	
	public static void init() {
		assets = new AssetManager();
		load_floors();
		load_walls();
		floors= new ArrayList<MapObject>();
		selectedCategory = categories[0];
		assets.finishLoading();
		assets.update();
		
		fileIterator = 0;
		categoryIterator = 0;
		history = new Stack<MapObject>();
		
	}
	public static void load_floors() {
		for(int i=0;i<floorFiles.length;i++) {
			assets.load(floorPath+floorFiles[i],Model.class);
			
			
			}

	}
	public static void load_walls() {
		for(int i=0;i<wallFiles.length;i++) {
			assets.load(wallPath+wallFiles[i],Model.class);
			
			}
	}
	public static void next_model() {
		switch(selectedCategory) {
		case "floors":
		
		
		
			if(fileIterator>=floorFiles.length) {
				fileIterator = 0;
			}
			selectedFile = floorFiles[fileIterator];
			selectedModel = assets.get(selectedCategory+"/"+selectedFile);
			fileIterator++;
		break;
		case "walls": 
			if(fileIterator>=wallFiles.length) {
				fileIterator = 0;
			}
			selectedFile = wallFiles[fileIterator];
			selectedModel = assets.get(selectedCategory+"/"+selectedFile);
			fileIterator++;
		break;
		}
	}
	public static void next_category() {
		categoryIterator++;
		fileIterator = 0;
		if(categoryIterator>=categories.length) {
			categoryIterator = 0;
		}
		selectedCategory = categories[categoryIterator];
		next_model();
	}
	
	public static void place_model() {
		if(placedModel!=null) {
			history.add(placedModel);
			currentTransform = placedModel.transform;
		}
		placedModel = new MapObject( selectedCategory+"/"+selectedFile, selectedModel);
		System.out.println(selectedFile);
		if(currentTransform!=null) {
			placedModel.transform.set(currentTransform);
		}else {placedModel.transform.set(new Vector3(0,0,0), new Quaternion(), new Vector3(0.01f,0.01f,0.01f));
			}
		GameScreen.instances.add(placedModel);
		
		
	}
	public static void deleteLast() {
		if(history.size()>=1) {
			GameScreen.instances.removeValue(history.lastElement(), true);
		history.remove(history.size()-1);
		}
	}
	public static void incrementPosition(int x,int y,int z) {
		placedModel.transform.trn(x, y, z);
		
	}
	//idk if its clockwise
	public static void rotateClockwise() {
		placedModel.transform.rotate(new Vector3(0,1,0), 45);
		
	}
	
	public static void printMapJson() throws IOException {
		
		new AsyncMapWriter("TESTFILE.json", GameScreen.instances);
	}
	
	public static void loadMapFromFile() throws IOException{

		
		FileHandle handle = Gdx.files.external("maps/TESTFILE.json");
		File file = handle.file();

		
		System.out.println(file.getAbsolutePath());
		
		
		if(file.exists()!=true){try {
			throw new IOException("The map doesnt exist");
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		}
		try {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		Json json = new Json();
		String[] unprocessed = br.readLine().split(";");
		Array<MapObject> instances = GameScreen.instances;
		
		for(int i=0;i<unprocessed.length;i++) {
			String path = unprocessed[i].split("--")[0];
			
			Matrix4 transform = json.fromJson(Matrix4.class, unprocessed[i].split("--")[1]);
			
			instances.add(new MapObject(path,assets.get(path,Model.class)));
			instances.get(i).transform.set(transform);
			
		}}catch(Exception e) {
			System.out.println(e);
		}
	}
	}


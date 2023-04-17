package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AsyncMapWriter implements Runnable{
	Thread thread;
	FileWriter fw;
	BufferedWriter bw;
	File file;
	String fileName;
	Array<MapObject>instances;
	@Override
	public void run() {

		file = new File(Gdx.files.internal("maps/"+fileName).path());
		if(file.exists()!=true){try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		Json json = new Json();
		String content = "";
		for(int i = 0; i<instances.size;i++) {
			MapObject aux = instances.get(i);
			content += aux.path+"--"+json.toJson(aux.transform, Matrix4.class)+";";
			
			
		}
		try {
			fw = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
		try {
			
			bw.write(content);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public AsyncMapWriter (String fileName, Array<MapObject> instances){
		 thread = new Thread(this,"asyncWriter");
		 this.fileName = fileName;
		 this.instances = instances;
		 thread.start();
		
	 }
}

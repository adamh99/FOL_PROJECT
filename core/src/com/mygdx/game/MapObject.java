package com.mygdx.game;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;


//class for static unchangeable map meshes
public class MapObject extends ModelInstance{
		
	
	String path;
	
		
	public MapObject(String path, Model model) {
		super(model);
		
		this.path=path;
		
	}	
	
	
	
	
}

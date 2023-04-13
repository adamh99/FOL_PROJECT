package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class MyFolGame extends Game {

	public void create() {

		new TitleScreen(this);
	}



	public void render() {
		super.render();
		//important
	}

	public void dispose() {

	}
}
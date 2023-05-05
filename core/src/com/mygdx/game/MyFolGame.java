package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.TitleScreen;
import com.mygdx.game.Settings.AssetLoader;

public class MyFolGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new TitleScreen(this));
	}


	@Override
	public void render() {
		super.render();

	}

	@Override
	public void dispose() {

	}
}
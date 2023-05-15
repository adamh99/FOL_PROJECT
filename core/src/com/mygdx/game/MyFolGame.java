package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.SplashScreen;
import com.mygdx.game.Screens.TitleScreen;
import com.mygdx.game.Settings.AssetLoader;

public class MyFolGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}


	@Override
	public void render() {
		super.render();

	}

	@Override
	public void dispose() {

	}
}
package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.AppPreferences;
import com.mygdx.game.Screens.PreferencesScreen;
import com.mygdx.game.Screens.SplashScreen;
import com.mygdx.game.Screens.TitleScreen;
import com.mygdx.game.Settings.AssetLoader;

public class MyFolGame extends Game {

	private PreferencesScreen preferencesScreen;
	public static AppPreferences preferences;

	@Override
	public void create() {
		preferences = new AppPreferences();
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

	public AppPreferences getPreferences(){
		return this.preferences;
	}
}
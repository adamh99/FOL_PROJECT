package com.mygdx.game.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetManager {
    public static Skin skin;

    public static void load(){
        skin = new Skin(Gdx.files.internal("shadeui/uiskin.json"));
    }
}

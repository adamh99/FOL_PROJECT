package com.mygdx.game.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {
    public static Skin skin;
    public static Sound wrongsound;
    public static Sound rightsound;

    public static Music song;


    public static void load(){
        rightsound = Gdx.audio.newSound(Gdx.files.internal("right.mp3"));
        wrongsound = Gdx.audio.newSound(Gdx.files.internal("wrong.mp3"));
        song= Gdx.audio.newMusic(Gdx.files.internal("Town.mp3"));
        skin = new Skin(Gdx.files.internal("shadeui/uiskin.json"));
    }
}

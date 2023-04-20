package com.mygdx.game.Api;

public class SettingsResult {


    private int zombieMaxHealth;

    private int zombieDamage;

    private int zombieSpeed;

    public int getZombieMaxHealth() {
        return zombieMaxHealth;
    }

    public int getZombieDamage() {
        return zombieDamage;
    }

    public int getZombieSpeed() {
        return zombieSpeed;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public int getPlayerMaxHealth() {
        return playerMaxHealth;
    }

    public float getPlayerFireRate() {
        return playerFireRate;
    }

    private int playerSpeed;
    private int playerMaxHealth;

    private float playerFireRate;

}

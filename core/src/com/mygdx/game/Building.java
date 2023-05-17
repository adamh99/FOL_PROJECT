package com.mygdx.game;

public class Building {
    private String playerName;
    private Floor[] floors;

    public Building(String playerName, Floor[] floors) {
        this.playerName = playerName;
        this.floors = floors;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Floor[] getFloors() {
        return floors;
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }
}







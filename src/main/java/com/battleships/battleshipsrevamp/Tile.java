package com.battleships.battleshipsrevamp;

import javafx.scene.control.Label;

public class Tile extends Label {
    public static enum Status {
        INTACT,
        MISSED,
        HIT,
        DESTROYED,
        EXPLODED
    }

    private int x;
    private int y;
    private boolean isClicked = false;
    private Status state;

    public Tile() {
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public Status getState(){
        return state;
    }

    public void setState(Status state) {
        getStyleClass().clear();

        switch (state) {
            case INTACT -> getStyleClass().addAll("tile");
            case MISSED -> getStyleClass().addAll("missed-tile");
            case HIT -> getStyleClass().addAll("boat-hit-tile");
            case DESTROYED -> getStyleClass().addAll("boat-destroyed-tile");
            case EXPLODED -> getStyleClass().addAll("mine-exploded-tile");
        }
        this.state = state;
    }
}

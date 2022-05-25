package com.battleships.battleshipsrevamp;

import java.util.ArrayList;

public abstract class Ship implements GameObject {
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Type type;
    private int nbTiles;

    public enum Type {
        SLOOP,
        BRIGANTINE,
        GALLEON
    }

    public Ship() {
    }

    public Ship(Type type, int nbTiles) {
        this.type = type;
        this.nbTiles = nbTiles;
    }

    @Override
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    @Override
    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public void handleHit(Tile tile) {
       tile.setState(Tile.Status.HIT);
       if(isDestroyed()){
           for (Tile shipTile : tiles) {
               shipTile.setState(Tile.Status.DESTROYED);
           }
       }
    }

    public void enableCheatMode() {
        for (Tile tile: tiles){
            if(tile.getState() == Tile.Status.INTACT){
                tile.getStyleClass().clear();
                tile.getStyleClass().addAll("boat-tile");
            }
        }
    }

    public void disableCheatMode() {
        for (Tile tile: tiles){
            if(tile.getState() == Tile.Status.INTACT){
                tile.setState(Tile.Status.INTACT);
            }
        }
    }

    private boolean isDestroyed() {
        for (Tile tile : tiles) {
            if (tile.getState() == Tile.Status.INTACT) return false;
        }
        return true;
    }
}

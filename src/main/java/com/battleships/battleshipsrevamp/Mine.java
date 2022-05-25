package com.battleships.battleshipsrevamp;

import java.util.ArrayList;

public class Mine implements GameObject {
    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Mine() {
    }

    public void handleHit(Tile tile) {
        tile.setState(Tile.Status.EXPLODED);
    }

    public void enableCheatMode() {
        for (Tile tile : tiles) {
            tile.getStyleClass().clear();
            tile.getStyleClass().addAll("mine-tile");
        }
    }

    public void disableCheatMode() {
        for (Tile tile : tiles) {
            tile.setState(Tile.Status.INTACT);
        }
    }

    @Override
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    @Override
    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
}

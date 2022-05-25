package com.battleships.battleshipsrevamp;

import java.util.ArrayList;

public class Mine implements GameObject {
    private Tile tile = new Tile();
    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Mine() {
    }

    @Override
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void addMineTiles(Tile tile) {
        tiles.add(tile);
    }
}

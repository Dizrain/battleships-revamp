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

    public enum Status {
        DESTROYED,
        HIT,
        INTACT
    }

    public Ship() {
    }

    public Ship(Type type, int nbTiles) {
        this.type = type;
        this.nbTiles = nbTiles;
    }

    public Status getStatus() {
        Ship.Status status = Status.INTACT;

        if (tiles.size() == 0) {
            status = Status.DESTROYED;
        } else if (tiles.size() > 0 && tiles.size() < nbTiles) {
            status = Status.HIT;
        } else if (tiles.size() == nbTiles) {
            status = Status.INTACT;
        }

        return status;
    }

    @Override
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
}

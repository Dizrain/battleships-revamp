package com.battleships.battleshipsrevamp;

import java.util.ArrayList;

public interface GameObject {
    ArrayList<Tile> getTiles();
    void setTiles(ArrayList<Tile> tiles);
}

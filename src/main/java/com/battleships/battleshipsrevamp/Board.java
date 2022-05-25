package com.battleships.battleshipsrevamp;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private ArrayList<Tile> availableTiles = new ArrayList<>();
    private ArrayList<Tile> usedTiles = new ArrayList<>();
    private ArrayList<Ship> ships = new ArrayList<>();
    private ArrayList<Mine> mines = new ArrayList<>();
    private final int NB_SLOOPS = 3;
    private final int NB_BRIGANTINES = 2;
    private final int NB_GALLEONS = 1;
    private final int NB_MINES = 3;

    public Board() {
    }

    public void markAvailableTiles(Tile tile) {
        availableTiles.add(tile);
    }

    public void generate() throws Exception {
        int sloops = 0;
        while (sloops < NB_SLOOPS) {
            Ship res = generateShip(Ship.Type.SLOOP);
            if (res != null) {
                sloops++;
                ships.add(res);
            }
        }

        int brigantines = 0;
        while (brigantines < NB_BRIGANTINES) {
            Ship res = generateShip(Ship.Type.BRIGANTINE);
            if (res != null) {
                brigantines++;
                ships.add(res);
            }
        }

        int galleons = 0;
        while (galleons < NB_GALLEONS) {
            Ship res = generateShip(Ship.Type.GALLEON);
            if (res != null) {
                galleons++;
                ships.add(res);
            }
        }

        int mines = 0;
        while (mines < NB_MINES) {
            Mine res = generateMine();
            mines++;
        }
    }

    public void reset() throws Exception {
        for (Tile tile : usedTiles) {
            availableTiles.add(tile);
        }

        for (Tile tile : availableTiles) {
            tile.setState(Tile.Status.INTACT);
        }

        usedTiles = new ArrayList<>();
        ships = new ArrayList<>();
        mines = new ArrayList<>();
        generate();
    }

    private Ship generateShip(Ship.Type type) throws Exception {
        ArrayList<Tile> allTiles = new ArrayList();
        Tile currentTile = randomTile();
        Tile nextTile;
        char direction = randomDirection();

        int nbTiles = 0;

        switch (type) {
            case SLOOP -> nbTiles = Sloop.TILES;
            case BRIGANTINE -> nbTiles = Brigantine.TILES;
            case GALLEON -> nbTiles = Galleon.TILES;
        }

        while (allTiles.size() < nbTiles) {
            switch (direction) {
                case 't':
                    nextTile = findTile(currentTile.getX(), currentTile.getY() - 1);
                    if (nextTile != null) {
                        allTiles.add(nextTile);
                        currentTile = nextTile;
                    } else {
                        return null;
                    }
                    break;
                case 'r':
                    nextTile = findTile(currentTile.getX() + 1, currentTile.getY());
                    if (nextTile != null) {
                        allTiles.add(nextTile);
                        currentTile = nextTile;
                    } else {
                        return null;
                    }
                    break;
                case 'b':
                    nextTile = findTile(currentTile.getX(), currentTile.getY() + 1);
                    if (nextTile != null) {
                        allTiles.add(nextTile);
                        currentTile = nextTile;
                    } else {
                        return null;
                    }
                    break;
                case 'l':
                    nextTile = findTile(currentTile.getX() - 1, currentTile.getY());
                    if (nextTile != null) {
                        allTiles.add(nextTile);
                        currentTile = nextTile;
                    } else {
                        return null;
                    }
                    break;
            }
        }

        for (Tile tile : allTiles) {
            useTile(tile);
            tile.getStyleClass().addAll("boat-tile");
        }

        for (Tile tile : allTiles) {
            disableNeighbourTiles(tile);
        }

        Ship ship;

        switch (type) {
            case SLOOP:
                ship = new Sloop();
                ship.setTiles(allTiles);
                return ship;
            case BRIGANTINE:
                ship = new Brigantine();
                ship.setTiles(allTiles);
                return ship;
            case GALLEON:
                ship = new Galleon();
                ship.setTiles(allTiles);
                return ship;
            default:
                throw new Exception("Unknown type");
        }
    }

    public Mine generateMine() {
        Tile tile = randomTile();

        Mine mine = new Mine();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(tile);
        mine.setTiles(tiles);
        mines.add(mine);

        tile.getStyleClass().addAll("mine-tile");
        useTile(tile);

        return mine;
    }

    private Tile randomTile() {
        int index = getRandomIndex();
        return availableTiles.get(index);
    }

    private void useTile(Tile tile) {
        if (!usedTiles.contains(tile)) {
            usedTiles.add(tile);
        }

        if (availableTiles.contains(tile)) {
            availableTiles.remove(tile);
        }

        // TODO: Remove this line when done
        System.out.println("Available Size: " + availableTiles.size() + ", Used Size: " + usedTiles.size());
    }

    private void disableNeighbourTiles(Tile tile) {
        Tile leftTile = findTile(tile.getX() - 1, tile.getY());
        if (leftTile != null) {
            useTile(leftTile);
        }

        Tile topTile = findTile(tile.getX(), tile.getY() - 1);
        if (topTile != null) {
            useTile(topTile);
        }

        Tile bottomTile = findTile(tile.getX(), tile.getY() + 1);
        if (bottomTile != null) {
            useTile(bottomTile);
        }

        Tile rightTile = findTile(tile.getX() + 1, tile.getY());
        if (rightTile != null) {
            useTile(rightTile);
        }

        Tile topLeftTile = findTile(tile.getX() - 1, tile.getY() - 1);
        if (topLeftTile != null) {
            useTile(topLeftTile);
        }

        Tile bottomLeftTile = findTile(tile.getX() - 1, tile.getY() + 1);
        if (bottomLeftTile != null) {
            useTile(bottomLeftTile);
        }

        Tile topRightTile = findTile(tile.getX() + 1, tile.getY() - 1);
        if (topRightTile != null) {
            useTile(topRightTile);
        }

        Tile bottomRightTile = findTile(tile.getX() + 1, tile.getY() + 1);
        if (bottomRightTile != null) {
            useTile(bottomRightTile);
        }
    }

    private Tile findTile(int x, int y) {
        for (Tile tile : availableTiles) {
            if (x == tile.getX() && y == tile.getY()) {
                return tile;
            }
        }

        return null;
    }

    private int getRandomIndex() {
        Random r = new Random();
        int low = 1;
        int high = availableTiles.size();

        return r.nextInt(high - low) + low;
    }

    private char randomDirection() {
        Random r = new Random();
        int low = 1;
        int high = 4;
        int result = r.nextInt(high - low) + low;

        return switch (result) {
            case 1 -> 't';
            case 2 -> 'r';
            case 3 -> 'b';
            case 4 -> 'l';
            default -> 't';
        };
    }

    public Ship findShip(Tile tile) {
        for (Ship ship : ships) {
            if (ship.getTiles().contains(tile)) {
                return ship;
            }
        }

        return null;
    }

    public Mine findMine(Tile tile) {
        for (Mine mine : mines) {
            if (mine.getTiles().contains(tile)) {
                return mine;
            }
        }

        return null;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public ArrayList<Mine> getMines() {
        return mines;
    }
}

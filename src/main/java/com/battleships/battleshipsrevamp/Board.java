package com.battleships.battleshipsrevamp;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private static ArrayList<Tile> availableTiles = new ArrayList<>();
    private static ArrayList<Tile> usedTiles = new ArrayList<>();
    private static final int NB_SLOOPS = 3;
    private static final int NB_BRIGANTINES = 2;
    private static final int NB_GALLEONS = 1;
    private static final int NB_MINES = 3;

    public static void markAvailableTiles(Tile tile) {
        availableTiles.add(tile);
    }

    public static void generate() throws Exception {
        int sloops = 0;
        while (sloops < NB_SLOOPS) {
            Ship res = generateShip(Ship.Type.SLOOP);
            if (res != null) {
                sloops++;
            }
        }

        int brigantines = 0;
        while (brigantines < NB_BRIGANTINES) {
            Ship res = generateShip(Ship.Type.BRIGANTINE);
            if (res != null) {
                brigantines++;
            }
        }

        int galleons = 0;
        while (galleons < NB_GALLEONS) {
            Ship res = generateShip(Ship.Type.GALLEON);
            if (res != null) {
                galleons++;
            }
        }

        int mines = 0;
        while(mines < NB_MINES) {
            Mine res = generateMine();
            mines++;
        }
    }

    private static Ship generateShip(Ship.Type type) throws Exception {
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

    public static Mine generateMine() {
        Tile tile = randomTile();

        Mine mine = new Mine();
        mine.setTile(tile);
        mine.addMineTiles(tile);

        tile.getStyleClass().addAll("mine-tile");
        useTile(tile);

        return mine;
    }

    private static Tile randomTile() {
        int index = getRandomIndex();
        return availableTiles.get(index);
    }

    private static void useTile(Tile tile) {
        if (!usedTiles.contains(tile)) {
            usedTiles.add(tile);
        }

        if (availableTiles.contains(tile)) {
            availableTiles.remove(tile);
        }

        // TODO: Remove this line when done
        System.out.println("Available Size: " + availableTiles.size() + ", Used Size: " + usedTiles.size());
    }

    private static void disableNeighbourTiles(Tile tile) {
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
        if (leftTile != null) {
            useTile(leftTile);
        }

        Tile bottomLeftTile = findTile(tile.getX() - 1, tile.getY() + 1);
        if (leftTile != null) {
            useTile(leftTile);
        }

        Tile topRightTile = findTile(tile.getX() + 1, tile.getY() - 1);
        if (leftTile != null) {
            useTile(leftTile);
        }

        Tile bottomRightTile = findTile(tile.getX() + 1, tile.getY() + 1);
        if (leftTile != null) {
            useTile(leftTile);
        }
    }

    private static Tile findTile(int x, int y) {
        for (Tile tile : availableTiles) {
            if (x == tile.getX() && y == tile.getY()) {
                return tile;
            }
        }

        return null;
    }

    private static int getRandomIndex() {
        Random r = new Random();
        int low = 1;
        int high = availableTiles.size();

        return r.nextInt(high - low) + low;
    }

    private static char randomDirection() {
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
}

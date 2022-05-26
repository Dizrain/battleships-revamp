package com.battleships.battleshipsrevamp;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import javafx.scene.media.*;

import java.io.File;

public class Main extends Application {
    private static final int TILES = 10;
    private static final int TILE_SIZE = 40;
    private static final int PADDING = (int) (TILE_SIZE * 0.03d);
    private static final int MARGIN = 25;
    private final Board board = new Board();
    private final Tile[][] grid = new Tile[TILES][TILES];
    private final Pane root = new Pane();
    private final ToggleSwitch toggle = new ToggleSwitch();

    private final String baseFilePath = "src/main/resources/sounds/";

    private final String emptyTileSoundFile = baseFilePath + "emptyTile.wav";
    Media emptyTileSound = new Media(new File(emptyTileSoundFile).toURI().toString());

    private final String shipTileSoundFile = baseFilePath + "shipTile.mp3";
    Media shipTileSound = new Media(new File(shipTileSoundFile).toURI().toString());

    private final String shipDestroyedSoundFile = baseFilePath + "fullShip.mp3";
    Media shipDestroyedSound = new Media(new File(shipDestroyedSoundFile).toURI().toString());

    private final String mineSoundFile = baseFilePath + "mineTile.mp3";
    Media mineSound = new Media(new File(mineSoundFile).toURI().toString());

    private final String winSoundFile = baseFilePath + "win.mp3";
    Media winSound = new Media(new File(winSoundFile).toURI().toString());

    @Override
    public void start(Stage stage) throws Exception {
        populateGrid();
        root.getStylesheets().add("style.css");
        stage.setTitle("Battleships Revamp");
        stage.setResizable(true);
        int windowSize = ((TILES - 1) * PADDING + TILES * TILE_SIZE) + MARGIN * 2;

        StackPane gameMenu = new StackPane();
        gameMenu.setMaxWidth(windowSize);
        gameMenu.setMinWidth(windowSize);

        Text toggleSwitchText = new Text("Cheat mode: ");
        toggle.switchedOnProperty().addListener(
                new ChangeListener<Boolean>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Boolean> observable,
                            Boolean oldValue, Boolean newValue
                    ) {
                        if (newValue) {
                            board.setCheatMode(true);
                        } else {
                            board.setCheatMode(false);
                        }
                    }
                }
        );
        Button resetButton = new Button("New Game");
        resetButton.getStyleClass().addAll("btn", "btn-primary");
        HBox gameMenuPos = new HBox(15, toggleSwitchText, toggle, resetButton);
        gameMenu.getChildren().addAll(gameMenuPos);

        gameMenu.setLayoutY(windowSize - 15);
        gameMenu.setLayoutX(110);

        Scene scene = new Scene(root, windowSize, windowSize + 30);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);

        root.getChildren().add(gameMenu);

        EventHandler<ActionEvent> resetHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    toggle.setValue(false);
                    board.reset();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        resetButton.setOnAction(resetHandler);

        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    private void populateGrid() throws Exception {
        //Add tiles
        for (int i = 0; i < TILES; i++) {
            for (int j = 0; j < TILES; j++) {
                Tile tile = new Tile(i, j);
                board.markAvailableTiles(tile);
                tile.setTranslateX((i * TILE_SIZE + PADDING * i) + MARGIN);
                tile.setTranslateY((j * TILE_SIZE + PADDING * j) + MARGIN);
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                tile.setState(Tile.Status.INTACT);

                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // If the tile is a ship tile && is not clicked, then
                        if (tile.isClicked()) {
                            return;
                        }

                        tile.setClicked(true);

                        Ship ship = board.findShip(tile);
                        if (ship != null) {
                            ship.handleHit(tile);
                            if (!ship.isDestroyed()) {
                                MediaPlayer mediaPlayer = new MediaPlayer(shipTileSound);
                                mediaPlayer.play();
                            } else {
                                MediaPlayer mediaPlayer = new MediaPlayer(shipDestroyedSound);
                                mediaPlayer.play();
                            }
                            // Check if all ships are destroyed
                            boolean gameOver = true;
                            for (Ship ship1 : board.getShips()) {
                                if (!ship1.isDestroyed()) {
                                    gameOver = false;
                                    break;
                                }
                            }
                            if (gameOver) {
                                onWin();
                            }

                            return;
                        }

                        Mine mine = board.findMine(tile);
                        if (mine != null) {
                            mine.handleHit(tile);
                            MediaPlayer mediaPlayer = new MediaPlayer(mineSound);
                            mediaPlayer.play();
                            onDefeat();
                            return;
                        }

                        MediaPlayer mediaPlayer = new MediaPlayer(emptyTileSound);
                        mediaPlayer.play();
                        tile.setState(Tile.Status.MISSED);
                    }
                };

                tile.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

                grid[i][j] = tile;
                root.getChildren().add(tile);
            }
        }

        board.generate();
    }

    private void onWin() {
        toggle.setValue(true);
        lockTiles();
        MediaPlayer mediaPlayer = new MediaPlayer(winSound);
        mediaPlayer.play();
        Alert.display("You won!", "Congratulations! You destroyed all enemy ships.");
    }

    private void onDefeat() {
        toggle.setValue(true);
        lockTiles();
        Alert.display("You lost!", "Oh no, looks like you've exploded on a mine. You can try again!");

    }

    private void lockTiles() {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                grid[row][column].setClicked(true);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
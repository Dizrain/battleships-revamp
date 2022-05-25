package com.battleships.battleshipsrevamp;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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

public class Main extends Application {
    private static final int TILES = 10;
    private static final int TILE_SIZE = 40;
    private static final int PADDING = (int) (TILE_SIZE * 0.03d);
    private static final int MARGIN = 25;
    private final Board board = new Board();
    private final Tile[][] grid = new Tile[TILES][TILES];
    private final Pane root = new Pane();

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
        ToggleSwitch toggle = new ToggleSwitch();
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
        Button resetButton = new Button("Reset");
        HBox gameMenuPos = new HBox(15, toggleSwitchText, toggle, resetButton);
        gameMenu.getChildren().addAll(gameMenuPos);

        gameMenu.setLayoutY(windowSize - 15);
        gameMenu.setLayoutX(110);

        stage.setScene(new Scene(root, windowSize, windowSize + 30));
        root.getChildren().add(gameMenu);

        EventHandler<ActionEvent> resetHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
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
                            return;
                        }

                        Mine mine = board.findMine(tile);
                        if (mine != null) {
                            mine.handleHit(tile);
                            return;
                        }

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

    // TODO:
    // 3. Make something happen when all boats are destroyed
    // 4. Make something happen when mine is clicked

    public static void main(String[] args) {
        launch();
    }
}
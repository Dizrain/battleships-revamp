package com.battleships.battleshipsrevamp;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int TILES = 10;
    private static final int TILE_SIZE = 40;
    private static final int PADDING = (int) (TILE_SIZE * 0.03d);
    private static final int MARGIN = 25;
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

        Button playButton = new Button("Play");
        Button resetButton = new Button("Reset");
        HBox gameMenuPos = new HBox(15, playButton, resetButton);
        gameMenu.getChildren().addAll(gameMenuPos);

        gameMenu.setLayoutY(windowSize - 15);
        gameMenu.setLayoutX(182);

        stage.setScene(new Scene(root, windowSize, windowSize + 30));
        root.getChildren().add(gameMenu);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    private void populateGrid() throws Exception {
        //Add tiles
        for (int i = 0; i < TILES; i++) {
            for (int j = 0; j < TILES; j++) {
                Tile tile = new Tile(i, j);
                Board.markAvailableTiles(tile);
                tile.setTranslateX((i * TILE_SIZE + PADDING * i) + MARGIN);
                tile.setTranslateY((j * TILE_SIZE + PADDING * j) + MARGIN);
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                tile.getStyleClass().addAll("tile");

                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // If the tile is a ship tile && is not clicked, then
                        // If the tile is a mine tile && is not clicked, then
                        // Else
                        tile.getStyleClass().addAll("clicked-empty-tile");
                        tile.setClicked(true);
                    }
                };

                tile.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

                grid[i][j] = tile;
                root.getChildren().add(tile);
            }
        }

        Board.generate();
    }

    // TODO:
    // 1. Make different effects for MouseEvent depending on the tile clicked
    // 2. Make boats status work with clicks (Destroyed, etc)
    // 3. Make something happen when all boats are destroyed
    // 4. Make something happen when mine is clicked
    // 5. Implement the Play button
    // 6. Implement the Reset button
    // 7. Make all tiles invisible and only shown when clicked on them

    public static void main(String[] args) {
        launch();
    }
}
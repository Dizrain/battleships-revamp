package com.battleships.battleshipsrevamp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class Alert {
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(100);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.getStyleClass().addAll("btn", "btn-primary");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        window.setScene(scene);
        window.showAndWait();
    }
}

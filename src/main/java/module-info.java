module com.battleships.battleshipsrevamp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.battleships.battleshipsrevamp to javafx.fxml;
    exports com.battleships.battleshipsrevamp;
}
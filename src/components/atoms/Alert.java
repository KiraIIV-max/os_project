package src.components.atoms;

import javafx.scene.control.Label;

public class Alert {
    public static Label create(String message) {
        Label label = new Label("⚠ " + message);
        label.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-text-fill: #D93737;" +
            "-fx-font-family: 'Geist';"
        );
        return label;
    }
}

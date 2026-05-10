package src.components.atoms;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class Badge {
    public static Label create(String text) {
        Label badge = new Label(text);
        badge.setPadding(new Insets(4, 12, 4, 12));
        badge.setStyle(
            "-fx-background-color: #DCFCE7;" +
            "-fx-background-radius: 999px;" +
            "-fx-font-family: 'Geist';" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: 600;" +
            "-fx-text-fill: #059669;"
        );
        return badge;
    }
}

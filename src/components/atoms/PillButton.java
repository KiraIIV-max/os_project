package src.components.atoms;

import javafx.scene.control.Button;

public class PillButton {
    private final Button button;

    public PillButton(String text, Runnable action) {
        button = new Button(text);
        button.setStyle(
            "-fx-text-fill: #5B7FDB;" +
            "-fx-font-size: 12px;" +
            "-fx-font-family: 'Geist';" +
            "-fx-border-color: #5B7FDB;" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 999px;" +
            "-fx-background-color: #FAFBFD;" +
            "-fx-background-radius: 999px;" +
            "-fx-padding: 8px 24px;" +
            "-fx-cursor: hand;"
        );
        button.setOnAction(e -> { if (action != null) action.run(); });
    }

    public Button getNode() { return button; }
}

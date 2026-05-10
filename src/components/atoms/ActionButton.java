package src.components.atoms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ActionButton {
    private static final String ACCENT = "#5B7FDB";
    private static final String SOFT_BLUE = "#EEF3FF";
    private static final String WHITE = "#FAFBFD";
    private static final String ERROR = "#D93737";
    private static final String SOFT_RED = "#FDF2F2";
    private static final String FONT_FAMILY = "Geist";

    public enum Variant { OUTLINE, PRIMARY, DESTRUCTIVE }

    private final Button button;

    public ActionButton(String symbol, String text, Variant variant, Runnable action) {
        Label symbolLabel = new Label(symbol);
        Label textLabel = new Label(text);
        HBox content = new HBox(8, symbolLabel, textLabel);
        content.setAlignment(Pos.CENTER);

        boolean isPrimary = variant == Variant.PRIMARY;
        boolean isDestructive = variant == Variant.DESTRUCTIVE;
        
        String bg = isPrimary ? ACCENT :
                    isDestructive ? SOFT_RED : SOFT_BLUE;
        String fg = isPrimary ? WHITE :
                    isDestructive ? ERROR : ACCENT;
        String border = isDestructive ? ERROR : ACCENT;

        symbolLabel.setStyle(labelStyle(fg, 24, "400"));
        textLabel.setStyle(labelStyle(fg, 16, "500"));

        button = new Button();
        button.setMinHeight(40);
        button.setPadding(isPrimary ? new Insets(0, 40, 0, 40) : new Insets(0, 16, 0, 16));
        button.setGraphic(content);
        button.setStyle(buttonStyle(bg, border));
        button.setOnAction(e -> { if (action != null) action.run(); });
    }

    public Button getNode() { return button; }

    private String buttonStyle(String bg, String border) {
        return "-fx-background-color: " + bg + ";" +
               "-fx-background-radius: 8px;" +
               "-fx-border-color: " + border + ";" +
               "-fx-border-radius: 8px;" +
               "-fx-border-width: 1px;" +
               "-fx-font-size: 16px;" +
               "-fx-font-weight: 600;" +
               "-fx-cursor: hand;";
    }

    private String labelStyle(String color, int size, String weight) {
        return "-fx-font-family: '" + FONT_FAMILY + "';" +
               "-fx-font-size: " + size + "px;" +
               "-fx-font-weight: " + weight + ";" +
               "-fx-text-fill: " + color + ";";
    }
}

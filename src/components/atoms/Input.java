package src.components.atoms;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Input {
    private static final String TEXT_PRIMARY = "#20242B";
    private static final String TEXT_MUTED = "#9CA3AF";
    private static final String BORDER = "#D1D5DB";
    private static final String BORDER_FOCUS = "#5B7FDB";
    private static final String SURFACE = "#FFFFFF";
    private static final String FONT_FAMILY = "Geist";

    private TextField field;

    public Input(String labelText, String placeholder) {
        field = new TextField();
        field.setPromptText(placeholder);
        field.setPrefSize(176, 40);
        field.setMaxSize(176, 40);
        field.setPadding(new Insets(0, 16, 0, 16));
        field.setStyle(fieldStyle(BORDER));
        field.focusedProperty().addListener((obs, was, focused) ->
            field.setStyle(fieldStyle(focused ? BORDER_FOCUS : BORDER))
        );

        Label label = new Label(labelText);
        label.setStyle(
            "-fx-font-family: '" + FONT_FAMILY + "';" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-weight: 500;" +
            "-fx-font-size: 16px;"
        );

        VBox container = new VBox(8, label, field);
        this.container = container;
    }

    private final VBox container;

    public VBox getNode() { return container; }
    public String getText() { return field.getText(); }
    public void clear() { field.clear(); }

    private String fieldStyle(String borderColor) {
        return "-fx-background-color: " + SURFACE + ";" +
               "-fx-background-radius: 8px;" +
               "-fx-border-color: " + borderColor + ";" +
               "-fx-border-radius: 8px;" +
               "-fx-border-width: 1px;" +
               "-fx-font-family: '" + FONT_FAMILY + "';" +
               "-fx-font-size: 16px;" +
               "-fx-text-fill: " + TEXT_PRIMARY + ";" +
               "-fx-prompt-text-fill: " + TEXT_MUTED + ";";
    }
}

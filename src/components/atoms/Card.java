package src.components.atoms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Card {
    public static VBox create(String title, String hint, Node... children) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16, 12, 16, 12));
        card.setStyle(
            "-fx-background-color: #FAFBFD;" +
            "-fx-background-radius: 8px;" +
            "-fx-border-color: #E2E0DD;" +
            "-fx-border-radius: 8px;" +
            "-fx-border-width: 1px;"
        );

        if(title != null) {
            Label titleLabel = new Label(title);
            titleLabel.setStyle(
                "-fx-font-family: 'Geist';" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 600;" +
                "-fx-text-fill: #1E293B;"
            );

            if (hint != null) {
                Label hintLabel = new Label(hint);
                hintLabel.setStyle(
                    "-fx-font-family: 'Geist';" +
                    "-fx-font-size: 12px;" +
                    "-fx-text-fill: #64748B;"
                );
                HBox header = new HBox(titleLabel, hintLabel);
                HBox.setHgrow(titleLabel, Priority.ALWAYS);
                titleLabel.setMaxWidth(Double.MAX_VALUE);
                header.setAlignment(Pos.CENTER_LEFT);
                card.getChildren().add(header);
            } else {
                card.getChildren().add(titleLabel);
            }
        }

        card.getChildren().addAll(children);
        return card;
    }
}

package src.components.molecules;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import src.components.atoms.Badge;
import src.components.atoms.Card;

public class MetricCard {
    public static VBox create(String title,
                              String algo1Name, double algo1Value,
                              String algo2Name, double algo2Value) {
        VBox col1 = column(algo1Name, algo1Value, algo1Value <= algo2Value);
        VBox col2 = column(algo2Name, algo2Value, algo2Value < algo1Value);

        HBox columns = new HBox(24, col1, col2);

        VBox card = Card.create(title, null, columns);
        card.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    private static VBox column(String name, double value, boolean isWinner) {
        Label nameLabel = new Label(name);
        nameLabel.setStyle(
            "-fx-font-family: 'Geist';" +
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #64748B;"
        );

        Label valueLabel = new Label(String.format("%.1f", value) + " ms");
        valueLabel.setStyle(
            "-fx-font-family: 'Geist';" +
            "-fx-font-size: 22px;" +
            "-fx-font-weight: 700;" +
            "-fx-text-fill: #1E293B;"
        );

        VBox col = new VBox(4, nameLabel, valueLabel);
        if (isWinner) col.getChildren().add(Badge.create("Winner"));
        col.setMinWidth(100);
        return col;
    }
}

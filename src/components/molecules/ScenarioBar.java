package src.components.molecules;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import src.components.atoms.PillButton;

public class ScenarioBar {
    private final HBox node;

    public ScenarioBar(Runnable onBasic, Runnable onConflict, Runnable onFairness) {
        this(onBasic, onConflict, onFairness, null);
    }

    public ScenarioBar(Runnable onBasic, Runnable onConflict, Runnable onFairness, Runnable onValidation) {
        Label title = new Label("Load a scenario");
        title.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: 500;" +
            "-fx-font-family: Geist;"
        );

        HBox buttons = new HBox(16,
            new PillButton("Basic Workload", onBasic).getNode(),
            new PillButton("Burst/Priority Conflict", onConflict).getNode(),
            new PillButton("Fairness/Starvation", onFairness).getNode()
        );
        
        if (onValidation != null) {
            buttons.getChildren().add(new PillButton("Validation Demo", onValidation).getNode());
        }

        node = new HBox(16, title);
        node.getChildren().addAll(buttons.getChildren());
        node.setPrefWidth(1000);
        node.setAlignment(Pos.CENTER_LEFT);
    }

    public HBox getNode() { return node; }
}

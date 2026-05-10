package src.components.molecules;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import src.components.atoms.PillButton;

public class ScenarioBar {
    private final HBox node;

    public ScenarioBar(Runnable onBasic, Runnable onConflict, Runnable onFairness) {
        Label title = new Label("Load a scenario");
        title.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: 500;" +
            "-fx-font-family: Geist;"
        );

        node = new HBox(16,
            title,
            new PillButton("Basic Workload", onBasic).getNode(),
            new PillButton("Burst/Priority Conflict", onConflict).getNode(),
            new PillButton("Fairness/Starvation", onFairness).getNode()
        );
        node.setPrefWidth(952);
        node.setAlignment(Pos.CENTER_LEFT);
    }

    public HBox getNode() { return node; }
}

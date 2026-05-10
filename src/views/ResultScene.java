package src.views;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import src.model.Process;

public class ResultScene {
    private final ArrayList<Process> processes;

    public ResultScene(ArrayList<Process> processes) {
        this.processes = processes;
    }

    public VBox render() {
        VBox layout = new VBox(24);
        layout.setPadding(new Insets(40, 24, 40, 24));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #FAFBFD;");

        Label title = new Label("Simulation running with " + processes.size() + " processes.");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: 600; -fx-font-family: 'Geist';");

        layout.getChildren().add(title);
        return layout;
    }
}

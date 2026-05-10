package src.components.organisms;

import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import src.components.atoms.Alert;
import src.components.atoms.Table;
import src.model.Process;

public class ProcessTablePanel {
    private final VBox node;
    private final HBox alertBox;
    private final Table table;
    private final List<Process> processes;
    private final Consumer<Process> onDelete;
    private VBox tableNode;

    public ProcessTablePanel(List<Process> processes, Consumer<Process> onDelete) {
        this.processes = processes;
        this.onDelete = onDelete;
        this.table = new Table();

        alertBox = new HBox();
        alertBox.setAlignment(Pos.BOTTOM_LEFT);
        alertBox.setPrefWidth(952);

        tableNode = table.render(processes, onDelete);

        node = new VBox(24, alertBox, tableNode);
    }

    public VBox getNode() { return node; }

    public void showError(String message) {
        alertBox.getChildren().setAll(Alert.create(message));
    }

    public void clearError() {
        alertBox.getChildren().clear();
    }

    public void refresh() {
        VBox newTable = table.render(processes, onDelete);
        int index = node.getChildren().indexOf(tableNode);
        if (index >= 0) node.getChildren().set(index, newTable);
        tableNode = newTable;
    }
}

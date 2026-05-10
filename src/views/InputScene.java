package src.views;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import src.model.Process;
import src.utils.Validator;
import src.components.atoms.ActionButton;
import src.components.atoms.Divider;
import src.components.molecules.ProcessForm;
import src.components.molecules.ScenarioBar;
import src.components.organisms.ProcessTablePanel;

public class InputScene {
    private ArrayList<Process> processes = new ArrayList<>();
    private Validator validator = new Validator();
    private ProcessTablePanel tablePanel;
    private ProcessForm form;
    private final Consumer<ArrayList<Process>> onBeginSimulation;

    public InputScene(Consumer<ArrayList<Process>> onBeginSimulation) {
        this.onBeginSimulation = onBeginSimulation;
    }

    public VBox render() {
        tablePanel = new ProcessTablePanel(processes, this::handleDeleteProcess);
        form = new ProcessForm(this::handleAddProcess);

        ScenarioBar scenarios = new ScenarioBar(
            () -> loadScenario(new int[][]{ {1, 0, 5, 2}, {2, 1, 3, 1}, {3, 2, 8, 3}, {4, 3, 4, 2} }),
            () -> loadScenario(new int[][]{ {1, 0, 10, 3}, {2, 0, 3, 1}, {3, 1, 6, 2}, {4, 2, 2, 5} }),
            () -> loadScenario(new int[][]{ {1, 0, 3, 1}, {2, 0, 7, 2}, {3, 0, 2, 3}, {4, 0, 15, 5} }),
            () -> showValidationDemo()
        );

        ActionButton clearButton = new ActionButton(
            "×", "Clear", ActionButton.Variant.DESTRUCTIVE,
            () -> {
                processes.clear();
                validator.reset();
                tablePanel.clearError();
                tablePanel.refresh();
            }
        );
        ActionButton beginButton = new ActionButton(
            "→", "Begin Simulation", ActionButton.Variant.PRIMARY,
            () -> {
                if (processes.isEmpty()) {
                    tablePanel.showError("No process added yet.");
                    return;
                }
                if (onBeginSimulation != null) {
                    onBeginSimulation.accept(processes);
                }
            }
        );

        HBox actions = new HBox(16, beginButton.getNode(), clearButton.getNode());

        VBox layout = new VBox(24,
            form.getNode(),
            tablePanel.getNode(),
            Divider.create(),
            scenarios.getNode(),
            actions
        );
        layout.setPadding(new Insets(40, 24, 40, 24));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setFillWidth(false);
        layout.setStyle("-fx-background-color: #FAFBFD;");

        return layout;
    }

    private void loadScenario(int[][] processData) {
        processes.clear();
        validator.reset();

        for (int[] data : processData) {
            processes.add(new Process(data[0], data[1], data[2], data[3]));
            validator.registerPID(data[0]);
        }

        tablePanel.clearError();
        tablePanel.refresh();
    }

    private void handleDeleteProcess(Process process) {
        processes.remove(process);
        validator.removePID(process.getPid());
        tablePanel.clearError();
        tablePanel.refresh();
    }

    private void handleAddProcess(String[] fields) {
        String error = validator.getErrorMessage(fields[0], fields[1], fields[2], fields[3]);
        tablePanel.clearError();

        if (error != null) {
            tablePanel.showError(error);
            return;
        }

        int pid = Integer.parseInt(fields[0]);
        validator.registerPID(pid);

        processes.add(new Process(
            pid,
            Integer.parseInt(fields[1]),
            Integer.parseInt(fields[2]),
            Integer.parseInt(fields[3])
        ));
        tablePanel.refresh();
        form.clearFields();
    }

    private void showValidationDemo() {
        processes.clear();
        validator.reset();
        tablePanel.clearError();
        
        processes.add(new Process(1, 0, 5, 1));
        validator.registerPID(1);
        processes.add(new Process(2, 1, 3, 2));
        validator.registerPID(2);
        
        tablePanel.refresh();
        
        // Show validation error message
        tablePanel.showError("Validation Demo: Try adding invalid data. Examples:\n" +
            "• Duplicate PID (e.g., '1') → Error\n" +
            "• Negative arrival (e.g., '-1') → Error\n" +
            "• Zero/negative burst (e.g., '0') → Error\n" +
            "• Zero/negative priority (e.g., '0') → Error");
    }
}

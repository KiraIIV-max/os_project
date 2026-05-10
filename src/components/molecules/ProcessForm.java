package src.components.molecules;

import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import src.components.atoms.ActionButton;
import src.components.atoms.Input;

public class ProcessForm {
    private final Input pidInput;
    private final Input arrivalInput;
    private final Input burstInput;
    private final Input priorityInput;
    private final HBox node;

    public ProcessForm(Consumer<String[]> onSubmit) {
        pidInput = new Input("PID", "e.g. 1");
        arrivalInput = new Input("Arrival Time (ms)", "e.g. 0");
        burstInput = new Input("Burst Time (ms)", "e.g. 5");
        priorityInput = new Input("Priority", "e.g. 1");

        ActionButton addButton = new ActionButton("+", "Add Process", ActionButton.Variant.OUTLINE, () -> {
            onSubmit.accept(new String[]{
                pidInput.getText(),
                arrivalInput.getText(),
                burstInput.getText(),
                priorityInput.getText()
            });
        });

        node = new HBox(24,
            pidInput.getNode(),
            arrivalInput.getNode(),
            burstInput.getNode(),
            priorityInput.getNode(),
            addButton.getNode()
        );
        node.setAlignment(Pos.BOTTOM_LEFT);
    }

    public HBox getNode() { return node; }

    public void clearFields() {
        pidInput.clear();
        arrivalInput.clear();
        burstInput.clear();
        priorityInput.clear();
    }
}

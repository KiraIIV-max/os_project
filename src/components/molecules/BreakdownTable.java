package src.components.molecules;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import src.components.atoms.Card;
import src.model.Process;

public class BreakdownTable {
    private static final String[] HEADERS = {
        "PID", "Waiting time (ms)", "Turnaround time (ms)", "Response time (ms)"
    };

    public static VBox create(String title, List<Process> processes) {
        VBox rows = new VBox();
        rows.getChildren().add(row(HEADERS, true));

        for (Process p : processes) {
            rows.getChildren().add(row(new String[]{
                "P" + p.getPid(),
                String.valueOf(p.getWaitingTime()),
                String.valueOf(p.getTurnaroundTime()),
                String.valueOf(p.getResponseTime())
            }, false));
        }

        VBox card = Card.create(title, null, rows);
        card.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    private static HBox row(String[] values, boolean isHeader) {
        HBox row = new HBox();
        row.setMinHeight(40);
        row.setAlignment(Pos.CENTER);

        if (!isHeader) {
            row.setStyle(
                "-fx-border-color: transparent transparent #F0F0F0 transparent;" +
                "-fx-border-width: 1px 0 0 0;"
            );
        }

        for (int i = 0; i < values.length; i++) {
            Label cell = new Label(values[i]);
            cell.setAlignment(Pos.CENTER);

            if (i == 0) {
                cell.setMinWidth(48);
                cell.setPrefWidth(48);
                cell.setMaxWidth(48);
            } else {
                cell.setMinWidth(0);
                cell.setPrefWidth(0);
                cell.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(cell, Priority.ALWAYS);
            }

            cell.setStyle(
                "-fx-font-family: 'Geist';" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: " + (isHeader ? "600" : "400") + ";" +
                "-fx-text-fill: " + (isHeader ? "#1E293B" : "#64748B") + ";"
            );
            row.getChildren().add(cell);
        }

        return row;
    }
}

package src.components.atoms;

import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import src.model.Process;

public class Table {
    private static final String SURFACE = "#FFFFFF";
    private static final String HEADER = "#F2F3F5";
    private static final String BORDER = "#D1D5DB";
    private static final String TEXT_PRIMARY = "#20242B";
    private static final String TEXT_MUTED = "#9CA3AF";
    private static final String FONT_FAMILY = "Geist";
    private static final int[] COL_W = { 144, 216, 216, 288, 96 };
    private static final int TABLE_W = 960;
    private static final int BODY_H = 240;

    public VBox render(List<Process> processes) {
        return render(processes, null);
    }

    public VBox render(List<Process> processes, Consumer<Process> onDelete) {
        VBox table = new VBox();
        table.setPrefWidth(TABLE_W);
        table.setStyle(
            "-fx-background-color: " + SURFACE + ";" +
            "-fx-background-radius: 8px;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8px;" +
            "-fx-border-width: 1px;"
        );

        table.getChildren().add(header());

        if (processes == null || processes.isEmpty()) {
            table.getChildren().add(emptyState());
        } else {
            table.getChildren().add(scrollableRows(processes, onDelete));
        }

        return table;
    }

    private HBox header() {
        HBox h = new HBox();
        h.setMinHeight(56);
        h.setAlignment(Pos.CENTER);
        h.setStyle(
            "-fx-background-color: " + HEADER + ";" +
            "-fx-background-radius: 8px 8px 0 0;" +
            "-fx-border-color: transparent transparent " + BORDER + " transparent;" +
            "-fx-border-width: 0 0 1px 0;"
        );
        h.getChildren().addAll(
            cell("PID", 0, true),
            cell("Arrival Time (ms)", 1, true),
            cell("Burst Time (ms)", 2, true),
            cell("Priority (lower means higher priority)", 3, true),
            cell("Actions", 4, true)
        );
        return h;
    }

    private ScrollPane scrollableRows(List<Process> processes, Consumer<Process> onDelete) {
        VBox rows = new VBox();
        for (Process p : processes) {
            HBox row = new HBox();
            row.setMinHeight(56);
            row.setAlignment(Pos.CENTER);
            row.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: transparent transparent " + BORDER + " transparent;" +
                "-fx-border-width: 0 0 1px 0;"
            );
            row.getChildren().addAll(
                cell("P" + p.getPid(), 0, false),
                cell(String.valueOf(p.getArrivalTime()), 1, false),
                cell(String.valueOf(p.getBurstTime()), 2, false),
                cell(String.valueOf(p.getPriority()), 3, false),
                actionCell(p, onDelete)
            );
            rows.getChildren().add(row);
        }

        ScrollPane sp = new ScrollPane(rows);
        sp.setPrefViewportWidth(TABLE_W);
        sp.setPrefViewportHeight(BODY_H);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        return sp;
    }

    private StackPane actionCell(Process process, Consumer<Process> onDelete) {
        StackPane cell = new StackPane();
        Button del = new Button("Del");
        cell.setPrefWidth(COL_W[4]);
        cell.setMinWidth(COL_W[4]);
        cell.setMaxWidth(COL_W[4]);
        del.setMinSize(32, 32);
        del.setPrefSize(32, 32);
        del.setAlignment(Pos.CENTER);
        del.setPadding(new Insets(0));
        del.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-font-family: '" + FONT_FAMILY + "';" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: 700;" +
            "-fx-text-fill: #D93737;" +
            "-fx-cursor: hand;"
        );
        del.setOnAction(e -> { if (onDelete != null) onDelete.accept(process); });
        cell.getChildren().add(del);
        return cell;
    }

    private StackPane emptyState() {
        Label line1 = styledLabel("No processes added yet.", TEXT_MUTED);
        Label line2 = styledLabel("Add a process to get started.", TEXT_MUTED);
        VBox msg = new VBox(4, line1, line2);
        msg.setAlignment(Pos.CENTER);
        StackPane pane = new StackPane(msg);
        pane.setMinHeight(BODY_H);
        return pane;
    }

    private Label cell(String text, int col, boolean isHeader) {
        Label l = new Label(text);
        l.setAlignment(Pos.CENTER);
        l.setPrefWidth(COL_W[col]);
        l.setMinWidth(COL_W[col]);
        l.setMaxWidth(COL_W[col]);
        l.setStyle(
            "-fx-font-family: '" + FONT_FAMILY + "';" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: " + (isHeader ? "600" : "400") + ";" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );
        return l;
    }

    private Label styledLabel(String text, String color) {
        Label l = new Label(text);
        l.setStyle(
            "-fx-font-family: '" + FONT_FAMILY + "';" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: 500;" +
            "-fx-text-fill: " + color + ";"
        );
        return l;
    }
}

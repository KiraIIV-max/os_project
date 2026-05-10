package src.components.molecules;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import src.components.atoms.Card;
import src.model.Process;
import src.utils.MetricsCalculator;

public class ComparisonSummary {
    public static VBox create(List<Process> srtfProcesses, List<Process> priorityProcesses) {
        double srtfAvgWT = MetricsCalculator.avgWaitingTime(srtfProcesses);
        double prioAvgWT = MetricsCalculator.avgWaitingTime(priorityProcesses);
        double srtfAvgTAT = MetricsCalculator.avgTurnaroundTime(srtfProcesses);
        double prioAvgTAT = MetricsCalculator.avgTurnaroundTime(priorityProcesses);
        double srtfAvgRT = MetricsCalculator.avgResponseTime(srtfProcesses);
        double prioAvgRT = MetricsCalculator.avgResponseTime(priorityProcesses);

        VBox content = new VBox(12);
        content.setStyle("-fx-padding: 16px;");

        // Analysis items
        addAnalysisItem(content, "Waiting Time Winner",
            srtfAvgWT <= prioAvgWT ? "SRTF (Shortest Jobs First)" : "Priority Scheduling",
            srtfAvgWT <= prioAvgWT);

        addAnalysisItem(content, "Turnaround Time Winner",
            srtfAvgTAT <= prioAvgTAT ? "SRTF (Shortest Jobs First)" : "Priority Scheduling",
            srtfAvgTAT <= prioAvgTAT);

        addAnalysisItem(content, "Response Time Winner",
            srtfAvgRT <= prioAvgRT ? "SRTF (Shortest Jobs First)" : "Priority Scheduling",
            srtfAvgRT <= prioAvgRT);

        // Starvation detection
        boolean srtfStarvation = MetricsCalculator.hasStarvation(srtfProcesses);
        boolean prioStarvation = MetricsCalculator.hasStarvation(priorityProcesses);

        if (srtfStarvation || prioStarvation) {
            addAnalysisItem(content, "Starvation Risk",
                (srtfStarvation ? "SRTF: High risk | " : "") +
                (prioStarvation ? "Priority: High risk" : ""),
                false);
        }

        // Max waiting times
        int srtfMaxWT = MetricsCalculator.getMaxWaitingTime(srtfProcesses);
        int prioMaxWT = MetricsCalculator.getMaxWaitingTime(priorityProcesses);
        addAnalysisItem(content, "Fairness (Lower Max Wait)",
            srtfMaxWT <= prioMaxWT ? "SRTF (" + srtfMaxWT + " ms)" : "Priority (" + prioMaxWT + " ms)",
            srtfMaxWT <= prioMaxWT);

        VBox card = Card.create("Comparison Summary", null, content);
        card.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    private static void addAnalysisItem(VBox parent, String label, String value, boolean isWinner) {
        Label labelNode = new Label(label + ":");
        labelNode.setStyle(
            "-fx-font-family: 'Geist';" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: 600;" +
            "-fx-text-fill: #64748B;"
        );

        Label valueNode = new Label(value);
        valueNode.setStyle(
            "-fx-font-family: 'Geist';" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: " + (isWinner ? "700" : "400") + ";" +
            "-fx-text-fill: " + (isWinner ? "#10B981" : "#64748B") + ";"
        );

        VBox item = new VBox(2, labelNode, valueNode);
        item.setAlignment(Pos.TOP_LEFT);
        parent.getChildren().add(item);
    }
}

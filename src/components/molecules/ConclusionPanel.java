package src.components.molecules;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import src.components.atoms.Card;
import src.model.Process;
import src.utils.MetricsCalculator;

public class ConclusionPanel {
    public static VBox create(List<Process> srtfProcesses, List<Process> priorityProcesses) {
        double srtfAvgWT = MetricsCalculator.avgWaitingTime(srtfProcesses);
        double prioAvgWT = MetricsCalculator.avgWaitingTime(priorityProcesses);
        double srtfAvgTAT = MetricsCalculator.avgTurnaroundTime(srtfProcesses);
        double prioAvgTAT = MetricsCalculator.avgTurnaroundTime(priorityProcesses);

        StringBuilder conclusion = new StringBuilder();

        int srtfWins = 0;
        if (srtfAvgWT <= prioAvgWT) srtfWins++;
        if (srtfAvgTAT <= prioAvgTAT) srtfWins++;

        conclusion.append("OVERALL PERFORMANCE:\n");
        if (srtfWins >= 2) {
            conclusion.append("SRTF (Shortest Remaining Time First) performed better overall.\n");
            conclusion.append("It prioritizes shorter jobs, reducing average wait and turnaround times.\n\n");
        } else if (srtfWins == 0) {
            conclusion.append("Priority Scheduling performed better overall.\n");
            conclusion.append("It effectively prioritizes urgent processes, improving response times.\n\n");
        } else {
            conclusion.append("Both algorithms showed trade-offs in different metrics.\n\n");
        }

        conclusion.append("EFFICIENCY vs URGENCY:\n");
        conclusion.append("• SRTF optimizes for efficiency (minimizes total waiting time)\n");
        conclusion.append("• Priority optimizes for urgency (serves important jobs first)\n");
        conclusion.append("• Choose SRTF for batch processing; Priority for real-time systems\n\n");

        int srtfMaxWT = MetricsCalculator.getMaxWaitingTime(srtfProcesses);
        int prioMaxWT = MetricsCalculator.getMaxWaitingTime(priorityProcesses);
        conclusion.append("FAIRNESS ASSESSMENT:\n");
        if (srtfMaxWT <= prioMaxWT) {
            conclusion.append("• SRTF is fairer: max wait = ").append(srtfMaxWT).append(" ms\n");
        } else {
            conclusion.append("• Priority is fairer: max wait = ").append(prioMaxWT).append(" ms\n");
        }

        conclusion.append("• Lower max wait time indicates better fairness\n\n");

        boolean srtfStarve = MetricsCalculator.hasStarvation(srtfProcesses);
        boolean prioStarve = MetricsCalculator.hasStarvation(priorityProcesses);
        conclusion.append("STARVATION RISK:\n");
        if (!srtfStarve && !prioStarve) {
            conclusion.append("• No starvation detected in this workload\n");
        } else {
            if (srtfStarve) conclusion.append("• SRTF: Long jobs may wait indefinitely if short jobs keep arriving\n");
            if (prioStarve) conclusion.append("• Priority: Low-priority jobs may starve if high-priority jobs dominate\n");
        }

        conclusion.append("\nRECOMMENDATION:\n");
        if (srtfWins >= 2) {
            conclusion.append("Use SRTF for this workload to achieve optimal average response times.");
        } else if (srtfWins == 0) {
            conclusion.append("Use Priority Scheduling for this workload to serve urgent processes effectively.");
        } else {
            conclusion.append("Use Priority Scheduling if urgency matters; otherwise use SRTF for efficiency.");
        }

        VBox content = new VBox(8);
        content.setStyle("-fx-padding: 16px;");
        content.setAlignment(Pos.TOP_LEFT);

        Label conclusionText = new Label(conclusion.toString());
        conclusionText.setWrapText(true);
        conclusionText.setStyle(
            "-fx-font-family: 'Geist';" +
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #1E293B;" +
            "-fx-line-spacing: 2;"
        );
        content.getChildren().add(conclusionText);

        VBox card = Card.create("Conclusion & Recommendations", null, content);
        card.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }
}

package src.views;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import src.model.Process;
import src.components.atoms.*;
import src.components.molecules.*;
import src.ui.GanttChart;
import src.utils.MetricsCalculator;

public class ResultScene {
    private final List<Process> srtfProcesses;
    private final List<Integer> srtfGanttLog;
    private final List<Process> priorityProcesses;
    private final List<Integer> priorityGanttLog;
    private final Runnable onBack;

    public ResultScene(List<Process> srtfProcesses, List<Integer> srtfGanttLog,
                       List<Process> priorityProcesses, List<Integer> priorityGanttLog,
                       Runnable onBack) {
        this.srtfProcesses = srtfProcesses;
        this.srtfGanttLog = srtfGanttLog;
        this.priorityProcesses = priorityProcesses;
        this.priorityGanttLog = priorityGanttLog;
        this.onBack = onBack;
    }

    public ScrollPane render() {
        Label title = new Label("Simulation Results");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-font-family: 'Geist'; -fx-text-fill: #1E293B;");

        Label subtitle = new Label("Comparing Shortest Remaining Time First (SRTF) vs Priority Scheduling.");
        subtitle.setStyle("-fx-font-size: 16px; -fx-font-family: 'Geist'; -fx-text-fill: #64748B;");

        HBox metrics = new HBox(16,
            MetricCard.create("Avg. Waiting Time",
                "SRTF", MetricsCalculator.avgWaitingTime(srtfProcesses),
                "Priority", MetricsCalculator.avgWaitingTime(priorityProcesses)),
            MetricCard.create("Avg. Turnaround Time",
                "SRTF", MetricsCalculator.avgTurnaroundTime(srtfProcesses),
                "Priority", MetricsCalculator.avgTurnaroundTime(priorityProcesses)),
            MetricCard.create("Avg. Response Time",
                "SRTF", MetricsCalculator.avgResponseTime(srtfProcesses),
                "Priority", MetricsCalculator.avgResponseTime(priorityProcesses))
        );

        HBox breakdowns = new HBox(16,
            BreakdownTable.create("SRTF Breakdown", srtfProcesses),
            BreakdownTable.create("Priority Breakdown", priorityProcesses)
        );

        VBox srtfGantt = Card.create("SRTF Gantt Chart", "Time (ms)",
            GanttChart.createGanttCanvas(srtfGanttLog));

        VBox priorityGantt = Card.create("Priority Gantt Chart", "Time (ms)",
            GanttChart.createGanttCanvas(priorityGanttLog));

        srtfGantt.setAlignment(Pos.CENTER);
        priorityGantt.setAlignment(Pos.CENTER);

        ActionButton backBtn = new ActionButton("←", "Back", ActionButton.Variant.OUTLINE, onBack);

        VBox comparison = ComparisonSummary.create(srtfProcesses, priorityProcesses);
        VBox conclusion = ConclusionPanel.create(srtfProcesses, priorityProcesses);

        VBox layout = new VBox(24,
            title, subtitle,
            metrics, breakdowns,
            srtfGantt, priorityGantt,
            comparison,
            conclusion,
            backBtn.getNode()
        );
        layout.setPadding(new Insets(40, 24, 40, 24));
        layout.setMaxWidth(1000);
        layout.setStyle("-fx-background-color: #FAFBFD;");

        ScrollPane scroll = new ScrollPane(layout);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #FAFBFD; -fx-background: #FAFBFD;");
        return scroll;
    }
}

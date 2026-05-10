package src.utils;

import java.util.List;
import src.model.Process;

public class MetricsCalculator {
    public static void computeMetrics(List<Process> processes) {
        for (Process p : processes) {
            p.setTurnaroundTime(p.getCompletionTime() - p.getArrivalTime());
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
            p.setResponseTime(p.getFirstRunTime() - p.getArrivalTime());
        }
    }

    public static double avgWaitingTime(List<Process> processes) {
        return processes.stream().mapToInt(Process::getWaitingTime).average().orElse(0);
    }

    public static double avgTurnaroundTime(List<Process> processes) {
        return processes.stream().mapToInt(Process::getTurnaroundTime).average().orElse(0);
    }

    public static double avgResponseTime(List<Process> processes) {
        return processes.stream().mapToInt(Process::getResponseTime).average().orElse(0);
    }

    // Analysis methods
    public static int getMaxWaitingTime(List<Process> processes) {
        return processes.stream().mapToInt(Process::getWaitingTime).max().orElse(0);
    }

    public static int getMinWaitingTime(List<Process> processes) {
        return processes.stream().mapToInt(Process::getWaitingTime).min().orElse(0);
    }

    public static boolean hasStarvation(List<Process> processes) {
        int maxWait = getMaxWaitingTime(processes);
        return maxWait > 50; // More than 50ms considered starvation
    }

    public static String getWinner(double algo1Value, double algo2Value) {
        if (algo1Value < algo2Value) return "SRTF";
        if (algo2Value < algo1Value) return "Priority";
        return "Tie";
    }

    public static String getAnalysisText(List<Process> srtfProcesses, List<Process> priorityProcesses) {
        double srtfAvgWT = avgWaitingTime(srtfProcesses);
        double prioAvgWT = avgWaitingTime(priorityProcesses);
        double srtfAvgTAT = avgTurnaroundTime(srtfProcesses);
        double prioAvgTAT = avgTurnaroundTime(priorityProcesses);
        double srtfAvgRT = avgResponseTime(srtfProcesses);
        double prioAvgRT = avgResponseTime(priorityProcesses);

        StringBuilder analysis = new StringBuilder();
        analysis.append("• Average Waiting Time: ");
        analysis.append(srtfAvgWT <= prioAvgWT ? "SRTF is better" : "Priority is better").append("\n");
        analysis.append("• Average Turnaround Time: ");
        analysis.append(srtfAvgTAT <= prioAvgTAT ? "SRTF is better" : "Priority is better").append("\n");
        analysis.append("• Average Response Time: ");
        analysis.append(srtfAvgRT <= prioAvgRT ? "SRTF is better" : "Priority is better").append("\n");

        if (hasStarvation(srtfProcesses)) analysis.append("• SRTF: Potential starvation risk detected\n");
        if (hasStarvation(priorityProcesses)) analysis.append("• Priority: Potential starvation risk detected\n");

        return analysis.toString();
    }
}

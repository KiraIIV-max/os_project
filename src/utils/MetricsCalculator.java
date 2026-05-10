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
}

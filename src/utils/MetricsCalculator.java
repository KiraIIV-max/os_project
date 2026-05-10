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
        int sum = 0;
        for (Process p : processes) sum += p.getWaitingTime();
        return processes.isEmpty() ? 0 : (double) sum / processes.size();
    }

    public static double avgTurnaroundTime(List<Process> processes) {
        int sum = 0;
        for (Process p : processes) sum += p.getTurnaroundTime();
        return processes.isEmpty() ? 0 : (double) sum / processes.size();
    }

    public static double avgResponseTime(List<Process> processes) {
        int sum = 0;
        for (Process p : processes) sum += p.getResponseTime();
        return processes.isEmpty() ? 0 : (double) sum / processes.size();
    }

    public static int getMaxWaitingTime(List<Process> processes) {
        int max = 0;
        for (Process p : processes) if (p.getWaitingTime() > max) max = p.getWaitingTime();
        return max;
    }

    public static int getMinWaitingTime(List<Process> processes) {
        if (processes.isEmpty()) return 0;
        int min = processes.get(0).getWaitingTime();
        for (Process p : processes) if (p.getWaitingTime() < min) min = p.getWaitingTime();
        return min;
    }

    public static boolean hasStarvation(List<Process> processes) {
        int maxWait = getMaxWaitingTime(processes);
        return maxWait > 50;
    }
}

package src.scheduler;

import src.model.Process;
import src.utils.MetricsCalculator;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseScheduler {
    protected abstract Process selectNext(List<Process> processes, int currentTime);

    public List<Integer> schedule(List<Process> processes) {
        if (processes == null) throw new IllegalArgumentException("Process list cannot be null");

        for (Process p : processes) {
            p.reset();
        }

        List<Integer> ganttLog = new ArrayList<>();

        int currentTime = 0;
        int completed = 0;
        int n = processes.size();
        int maxTime = calculateMaxTime(processes);

        while (completed < n && currentTime < maxTime) {
            Process next = selectNext(processes, currentTime);

            if (next == null) {
                ganttLog.add(-1);
                currentTime++;
                continue;
            }

            if (!next.isStarted()) {
                next.setStarted(true);
                next.setFirstRunTime(currentTime);
            }

            next.setRemainingTime(next.getRemainingTime() - 1);

            ganttLog.add(next.getPid());

            if (next.getRemainingTime() == 0) {
                next.setCompletionTime(currentTime + 1);
                completed++;
            }

            currentTime++;
        }

        MetricsCalculator.computeMetrics(processes);

        return ganttLog;
    }

    private int calculateMaxTime(List<Process> processes) {
        int maxArrival = 0;
        int totalBurst = 0;
        for (Process p : processes) {
            maxArrival = Math.max(maxArrival, p.getArrivalTime());
            totalBurst += p.getBurstTime();
        }
        return maxArrival + totalBurst;
    }
}

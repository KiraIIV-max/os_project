import java.util.*;

public class SJFScheduler {

    public static List<Integer> schedule(List<Process> processes) {

        for(Process p : processes){
            p.reset();
        }

        List<Integer> ganttLog = new ArrayList<>();

        int currentTime = 0;
        int completed = 0;
        int n = processes.size();

        while (completed < n) {

            Process shortest = null;

            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {

                    if (shortest == null || p.remainingTime < shortest.remainingTime ||
                        (p.remainingTime == shortest.remainingTime && p.arrivalTime < shortest.arrivalTime)) {
                        shortest = p;
                    }
                }
            }

            if (shortest == null) {
                ganttLog.add(-1);
                currentTime++;
                continue;
            }

            if (!shortest.started) {
                shortest.started = true;
                shortest.firstRunTime = currentTime;
            }

            shortest.remainingTime--;

            ganttLog.add(shortest.pid);

            if (shortest.remainingTime == 0) {
                shortest.completionTime = currentTime + 1;
                completed++;
            }

            currentTime++;
        }

        for (Process p : processes) {
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            p.responseTime = p.firstRunTime - p.arrivalTime;
        }

        return ganttLog;
    }
}

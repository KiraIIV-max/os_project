import java.util.*;

public class PriorityScheduler {

    public static List<Integer> schedule(List<Process> processes) {

        for (Process p : processes) {
            p.started = false;
            p.remainingTime = p.burstTime;
            p.completionTime = 0;
            p.turnaroundTime = 0;
            p.waitingTime = 0;
            p.responseTime = 0;
        }

        List<Integer> ganttLog = new ArrayList<>();

        int currentTime = 0;
        int completed = 0;
        int n = processes.size();

        while (completed < n) {

            Process highest = null;

            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {

                    // Lower priority number = higher priority (Linux convention)
                    if (highest == null
                            || p.priority < highest.priority
                            || (p.priority == highest.priority && p.arrivalTime < highest.arrivalTime)) {
                        highest = p;
                    }
                }
            }

            if (highest == null) {
                ganttLog.add(-1);
                currentTime++;
                continue;
            }

            if (!highest.started) {
                highest.started = true;
                highest.firstRunTime = currentTime;
            }

            highest.remainingTime--;

            ganttLog.add(highest.pid);

            if (highest.remainingTime == 0) {
                highest.completionTime = currentTime + 1;
                completed++;
            }

            currentTime++;
        }

        for (Process p : processes) {
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime    = p.turnaroundTime - p.burstTime;
            p.responseTime   = p.firstRunTime   - p.arrivalTime;
        }

        return ganttLog;
    }
}
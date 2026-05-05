package src.scheduler;

import src.model.Process;

import java.util.List;

public class PriorityScheduler extends BaseScheduler {

    @Override
    protected Process selectNext(List<Process> processes, int currentTime) {
        Process highest = null;

        for (Process p : processes) {
            if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0) {
                if (highest == null || p.getPriority() < highest.getPriority() ||
                    (p.getPriority() == highest.getPriority() &&
                     p.getArrivalTime() < highest.getArrivalTime())) {
                    highest = p;
                }
            }
        }

        return highest;
    }
}

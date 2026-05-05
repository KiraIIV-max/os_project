package src.scheduler;

import src.model.Process;

import java.util.List;

public class SJFScheduler extends BaseScheduler {

    @Override
    protected Process selectNext(List<Process> processes, int currentTime) {
        Process shortest = null;

        for (Process p : processes) {
            if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0) {
                if (shortest == null || p.getRemainingTime() < shortest.getRemainingTime() ||
                    (p.getRemainingTime() == shortest.getRemainingTime() &&
                     p.getArrivalTime() < shortest.getArrivalTime())) {
                    shortest = p;
                }
            }
        }

        return shortest;
    }
}

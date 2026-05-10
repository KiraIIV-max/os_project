package src.utils;

import java.util.HashSet;

public class Validator {

    private HashSet<Integer> usedPIDs = new HashSet<>();

    public void reset() {
        usedPIDs.clear();
    }

    public String getErrorMessage(String pidStr, String arrivalStr, String burstStr, String priorityStr) {
        try {
            int pid = Integer.parseInt(pidStr);
            int arrival = Integer.parseInt(arrivalStr);
            int burst = Integer.parseInt(burstStr);
            int prio = Integer.parseInt(priorityStr);

            if (arrival < 0) return "Arrival time must be >= 0";
            if (burst <= 0) return "Burst time must be > 0";
            if (prio < 1) return "Priority must be >= 1";
            if (usedPIDs.contains(pid)) return "PID already exists";

            return null;
        } catch (NumberFormatException e) {
            return "Invalid input: must be numbers only";
        }
    }

    public void registerPID(int pid) {
        usedPIDs.add(pid);
    }

    public void removePID(int pid) {
        usedPIDs.remove(pid);
    }
}

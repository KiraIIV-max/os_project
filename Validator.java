import java.util.HashSet;

public class Validator {

    private static HashSet<Integer> usedPIDs = new HashSet<>();

    public static void reset() {
        usedPIDs.clear();
    }

    public static boolean isValidProcess(String pidStr, String arrivalStr, String burstStr, String priorityStr) {
        try {
            int pid = Integer.parseInt(pidStr);
            int arrival = Integer.parseInt(arrivalStr);
            int burst = Integer.parseInt(burstStr);
            int priority = Integer.parseInt(priorityStr);

            if (arrival < 0) {
                System.out.println("Arrival time must be >= 0");
                return false;
            }

            if (burst <= 0) {
                System.out.println("Burst time must be > 0");
                return false;
            }

            if (priority < 1) {
                System.out.println("Priority must be >= 1");
                return false;
            }

            if (usedPIDs.contains(pid)) {
                System.out.println("PID already exists");
                return false;
            }

            usedPIDs.add(pid);

            return true;

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: must be numbers only");
            return false;
        }
    }
    public static void removePID(int pid) {
        usedPIDs.remove(pid);
    }
}
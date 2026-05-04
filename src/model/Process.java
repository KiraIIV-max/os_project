package src.model;
public class Process {
    public int pid;
    public int arrivalTime;
    public int burstTime;
    public int priority;

    public int remainingTime;

    public int completionTime;
    public int waitingTime;
    public int turnaroundTime;
    public int responseTime;

    public boolean started;
    public int firstRunTime;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;

        this.remainingTime = burstTime;

        this.started = false;
        this.firstRunTime = -1;
    }
    public void reset() {
        this.remainingTime = burstTime;
        this.started = false;
        this.firstRunTime = -1;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.responseTime = 0;
    }
}


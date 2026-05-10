package src.model;

public class Process {
    private int pid;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int remainingTime;
    private int completionTime;
    private int waitingTime;
    private int turnaroundTime;
    private int responseTime;
    private boolean started;
    private int firstRunTime;

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

    public int getPid() { return pid; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getPriority() { return priority; }
    public int getRemainingTime() { return remainingTime; }
    public int getCompletionTime() { return completionTime; }
    public int getWaitingTime() { return waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public int getResponseTime() { return responseTime; }
    public boolean isStarted() { return started; }
    public int getFirstRunTime() { return firstRunTime; }

    public void setPid(int pid) { this.pid = pid; }
    public void setArrivalTime(int arrivalTime) { this.arrivalTime = arrivalTime; }
    public void setBurstTime(int burstTime) { this.burstTime = burstTime; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }
    public void setCompletionTime(int completionTime) { this.completionTime = completionTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    public void setResponseTime(int responseTime) { this.responseTime = responseTime; }
    public void setStarted(boolean started) { this.started = started; }
    public void setFirstRunTime(int firstRunTime) { this.firstRunTime = firstRunTime; }
}

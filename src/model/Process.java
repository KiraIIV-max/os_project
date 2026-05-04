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

    public Process() {}

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
    public int getCompletionTime() { return completionTime; }
    public int getWaitingTime() { return waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public int getResponseTime() { return responseTime; }

    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    public void setResponseTime(int responseTime) { this.responseTime = responseTime; }

    public void setPid(int pid) { this.pid = pid; }
}
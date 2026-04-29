public class Process {
   int pid;
   int arrivalTime;
   int burstTime;
   int priority;
   int remainingTime;
   int completionTime;
   int waitingTime;
   int turnaroundTime;
   int responseTime;
   boolean started;
   int firstRunTime;

   public Process(int var1, int var2, int var3, int var4) {
      this.pid = var1;
      this.arrivalTime = var2;
      this.burstTime = var3;
      this.priority = var4;
      this.remainingTime = var3;
      this.started = false;
      this.firstRunTime = -1;
   }
}
# SRTF vs Priority Scheduler Simulator

A JavaFX-based interactive simulator that compares two CPU process scheduling algorithms: **Shortest Remaining Time First (SRTF)** and **Priority Scheduling**.

## 🎯 Overview

This application allows you to input a list of processes with their characteristics and visualize how different scheduling algorithms handle them. It provides side-by-side comparison with detailed metrics and Gantt charts to help understand the differences between preemptive SRTF and priority-based scheduling.

## ✨ Features

- **Two Scheduling Algorithms**
  - SRTF (Shortest Remaining Time First) - Preemptive
  - Priority Scheduler - Based on process priority

- **Process Input Form**
  - Add processes with: PID, Arrival Time, Burst Time, Priority
  - Real-time validation of input
  - Clear error messages for invalid data

- **Visual Output**
  - **Gantt Charts** - Timeline visualization of process execution
  - **Metric Cards** - Key performance indicators (wait time, turnaround time, etc.)
  - **Detailed Breakdown Tables** - Process-by-process analysis
  - **Comparison Summary** - Side-by-side comparison of both algorithms
  - **Recommendations** - Insights on which algorithm is better for your scenario

- **Pre-built Scenarios**
  - Quick-load example datasets for learning

## 🛠️ Tech Stack

- **Language**: Java 11+
- **UI Framework**: JavaFX
- **Architecture**: Component-based (Atoms → Molecules → Organisms)

## 📦 Project Structure

```
src/
├── model/                    # Data models
│   └── Process.java         # Process entity
│
├── scheduler/               # Scheduling algorithms
│   ├── BaseScheduler.java   # Base class for schedulers
│   ├── SJFScheduler.java    # SRTF implementation
│   └── PriorityScheduler.java
│
├── ui/                      # UI rendering
│   ├── App.java            # Entry point
│   └── GanttChart.java     # Timeline visualization
│
├── views/                  # Application screens
│   ├── InputScene.java     # Data input screen
│   └── ResultScene.java    # Results display screen
│
├── components/            # Reusable UI components
│   ├── atoms/             # Basic elements (buttons, inputs, cards)
│   ├── molecules/         # Composite components (forms, tables)
│   └── organisms/         # Complex panels
│
└── utils/                 # Helper utilities
    ├── Validator.java     # Input validation
    └── MetricsCalculator.java  # Performance metrics
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- JavaFX SDK

### Running the Application

**Windows:**

```bash
build_run.bat
```

**Manual:**

```bash
javac -d bin src/**/*.java
java -cp bin src.ui.App
```

## 📝 How to Use

1. **Start the Application**
   - The input screen appears with a process form

2. **Add Processes**
   - Fill in the form with process details:
     - **PID**: Process identifier (unique number)
     - **Arrival Time**: When the process arrives at the CPU
     - **Burst Time**: How long the process needs to run
     - **Priority**: Priority level (1-10, lower is higher priority)
   - Click "Add Process" to add to the list

3. **Load Example Scenarios** (Optional)
   - Use scenario buttons to load pre-configured examples

4. **Run Simulation**
   - Click "Simulate" to compare algorithms

5. **View Results**
   - **Gantt Charts**: See process execution timeline for each algorithm
   - **Metrics**: Compare wait times, turnaround times, CPU utilization
   - **Summary**: Get recommendations on which algorithm is better

## 📊 Key Metrics Explained

- **Wait Time**: Time a process spends waiting in the queue
- **Turnaround Time**: Total time from arrival to completion
- **Average Wait Time**: Mean wait time across all processes
- **CPU Utilization**: Percentage of time CPU is actively executing

## 🔍 Algorithm Details

### SRTF (Shortest Remaining Time First)

- **Type**: Preemptive
- **Strategy**: Always executes the process with the shortest remaining time
- **Best for**: Minimizing average waiting time
- **Trade-off**: Context switches may increase overhead

### Priority Scheduling

- **Type**: Non-preemptive
- **Strategy**: Executes processes based on assigned priority levels
- **Best for**: Real-time systems with varied task importance
- **Trade-off**: Lower priority processes may starve

## 🎓 Learning Objectives

This simulator helps you understand:

- How different scheduling algorithms affect process execution
- The impact of preemption vs non-preemption
- Trade-offs between fairness and efficiency
- Real-world scheduling challenges in operating systems

## 📄 Example Workflow

```
Input:
  Process 1: PID=1, Arrival=0, Burst=8, Priority=2
  Process 2: PID=2, Arrival=1, Burst=4, Priority=1
  Process 3: PID=3, Arrival=2, Burst=2, Priority=3

Output:
  SRTF Gantt:     [P1(6)] [P2(4)] [P1(2)] [P3(2)]
  Priority Gantt: [P1(8)] [P2(4)] [P3(2)]

  SRTF Better for: Average wait time
  Priority Better for: Task importance consideration
```

## 🐛 Known Limitations

- Maximum processes: 10
- Time values must be positive integers
- Priority values: 1-10 (lower = higher priority)

## 📖 Documentation

For detailed architecture information, see [PROJECT_ARCHITECTURE.md](PROJECT_ARCHITECTURE.md)

## 🤝 Contributing

Improvements and suggestions are welcome!

## 📝 License

This project is for educational purposes.

---

**Made for understanding CPU Scheduling in Operating Systems** ⚙️  


## 📸 Screenshots

### Input Screen

<img width="1252" height="912" alt="Input Screen" src="https://github.com/user-attachments/assets/289afe12-e6b8-4780-a0cf-dd5ef21f54a0" />

### Results Screen - Metrics & Analysis

<img width="1257" height="908" alt="Results Screen" src="https://github.com/user-attachments/assets/93944554-8baf-4d0e-9e6c-073a81d1e7f3" />

### Results Screen - Gantt Charts & Comparison

<img width="1250" height="911" alt="Gantt Charts" src="https://github.com/user-attachments/assets/577e36c6-6853-4f2d-b64b-716321f84e99" />

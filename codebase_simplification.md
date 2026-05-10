# Codebase Simplification Analysis

I reviewed all 20 source files. Here's what can be simplified, ordered by impact:

---

## 1. Delete `ResultTable.java` — it's dead code
[ResultTable.java](file:///c:/Projects/faculty/os_project/src/ui/ResultTable.java) is **never imported or used anywhere**. The results view uses `BreakdownTable` instead. Safe to delete entirely.

---

## 2. Remove `getAnalysisText()` from MetricsCalculator — also dead code
[MetricsCalculator.java:L57-L77](file:///c:/Projects/faculty/os_project/src/utils/MetricsCalculator.java#L57-L77) — the `getAnalysisText()` method is **never called** anywhere. `ComparisonSummary` and `ConclusionPanel` compute their own comparisons directly. Delete lines 57–77.

---

## 3. Fix double-validation bug in `InputScene.handleAddProcess()`
[InputScene.java:L105-L127](file:///c:/Projects/faculty/os_project/src/views/InputScene.java#L105-L127) — validation is called **twice**: first `getErrorMessage()` then `isValidProcess()`. Both parse the same strings and run the same checks. The second call also **registers the PID** as a side-effect, so if `getErrorMessage()` returns an error and you fix it, the PID may already be marked as used.

**Simplify to:**
```java
private void handleAddProcess(String[] fields) {
    String error = validator.getErrorMessage(fields[0], fields[1], fields[2], fields[3]);
    tablePanel.clearError();

    if (error != null) {
        tablePanel.showError(error);
        return;
    }

    // Register the PID (getErrorMessage already validated)
    validator.registerPID(Integer.parseInt(fields[0]));

    processes.add(new Process(
        Integer.parseInt(fields[0]),
        Integer.parseInt(fields[1]),
        Integer.parseInt(fields[2]),
        Integer.parseInt(fields[3])
    ));
    tablePanel.refresh();
    form.clearFields();
}
```

And in `Validator.java`, add a simple `registerPID(int pid)` method and remove `isValidProcess()` entirely (it duplicates `getErrorMessage` logic):

```diff
- public boolean isValidProcess(String pidStr, String arrivalStr, String burstStr, String priorityStr) {
-     // ... 15 lines of duplicated logic ...
- }

+ public void registerPID(int pid) {
+     usedPIDs.add(pid);
+ }
```

---

## 4. Remove unnecessary constructor overload in `ScenarioBar`
[ScenarioBar.java:L12-L14](file:///c:/Projects/faculty/os_project/src/components/molecules/ScenarioBar.java#L12-L14) — the 3-parameter constructor just calls the 4-parameter one with `null`. It's **never used**. Delete it.

---

## 5. Fix blank line issue in `BaseScheduler.java`
[BaseScheduler.java:L8-L9](file:///c:/Projects/faculty/os_project/src/scheduler/BaseScheduler.java#L8-L9) — has a stray blank line from the earlier edit. Minor cleanup.

---

## Summary of changes

| File | Action |
|------|--------|
| `ResultTable.java` | **Delete** (unused) |
| `MetricsCalculator.java` | Remove `getAnalysisText()` (unused) |
| `Validator.java` | Remove `isValidProcess()`, add `registerPID()` |
| `InputScene.java` | Fix double-validation, use `registerPID()` |
| `ScenarioBar.java` | Remove unused 3-param constructor |
| `BaseScheduler.java` | Remove stray blank line |

> [!NOTE]
> The rest of the codebase (Process.java, schedulers, UI components) is already clean and straightforward — for-loops, simple if/else, clear naming. No further simplification needed.

**Want me to apply all of these changes?**

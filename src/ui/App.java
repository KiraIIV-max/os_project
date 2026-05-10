package src.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import src.model.Process;
import src.views.InputScene;
import src.views.ResultScene;
import src.scheduler.SJFScheduler;
import src.scheduler.PriorityScheduler;

public class App extends Application {

    private Stage stage;
    private InputScene inputScene;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        inputScene = new InputScene(processes -> this.runSimulation(processes));

        Scene scene = new Scene(inputScene.render(), 1000, 700);
        stage.setTitle("SRTF vs Priority Simulator");
        stage.setMinHeight(700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void runSimulation(ArrayList<Process> processes) {
        ArrayList<Process> srtfList     = deepCopy(processes);
        ArrayList<Process> priorityList = deepCopy(processes);

        List<Integer> srtfGantt     = new SJFScheduler().schedule(srtfList);
        List<Integer> priorityGantt = new PriorityScheduler().schedule(priorityList);

        ResultScene resultScene = new ResultScene(
            srtfList, srtfGantt,
            priorityList, priorityGantt,
            this::goBackToInput
        );

        stage.setTitle("SRTF vs Priority || Results");
        stage.getScene().setRoot(resultScene.render());
    }

    private void goBackToInput() {
        stage.setTitle("SRTF vs Priority Simulator");
        stage.getScene().setRoot(inputScene.render());
    }

    private ArrayList<Process> deepCopy(ArrayList<Process> original) {
        ArrayList<Process> copy = new ArrayList<>();
        for (Process p : original) {
            copy.add(new Process(
                p.getPid(),
                p.getArrivalTime(),
                p.getBurstTime(),
                p.getPriority()
            ));
        }
        return copy;
    }
}

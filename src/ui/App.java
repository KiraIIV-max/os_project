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
    @Override
    public void start(Stage stage) {
        InputScene[] inputSceneHolder = new InputScene[1];
        inputSceneHolder[0] = new InputScene(processes -> {
            ArrayList<Process> srtfList = deepCopy(processes);
            ArrayList<Process> priorityList = deepCopy(processes);

            List<Integer> srtfGantt = new SJFScheduler().schedule(srtfList);
            List<Integer> priorityGantt = new PriorityScheduler().schedule(priorityList);

            ResultScene resultScene = new ResultScene(
                srtfList, srtfGantt,
                priorityList, priorityGantt,
                () -> {
                    stage.setTitle("SRTF vs Priority Simulator");
                    stage.getScene().setRoot(inputSceneHolder[0].render());
                }
            );
            stage.setTitle("SRTF vs Priority - Results");
            stage.getScene().setRoot(resultScene.render());
        });

        Scene scene = new Scene(inputSceneHolder[0].render(), 1000, 700);
        stage.setTitle("SRTF vs Priority Simulator");
        stage.setMinHeight(700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private ArrayList<Process> deepCopy(ArrayList<Process> original) {
        ArrayList<Process> copy = new ArrayList<>();
        for (Process p : original) {
            copy.add(new Process(p.getPid(), p.getArrivalTime(), p.getBurstTime(), p.getPriority()));
        }
        return copy;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

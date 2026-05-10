package src.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import src.views.InputScene;
import src.views.ResultScene;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        InputScene inputScene = new InputScene(processes -> {
            ResultScene resultScene = new ResultScene(processes);
            stage.getScene().setRoot(resultScene.render());
        });
        Scene scene = new Scene(inputScene.render(), 1000, 700);

        stage.setTitle("SJF vs Priority Simulator");
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

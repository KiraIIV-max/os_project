package src.ui;

import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import src.model.Process;

public class ResultTable {
        public VBox showComparisonTable(ArrayList<Process> priorityProcesses, ArrayList<Process> sjfProcesses) {
                TableView<Process> priorityTable = createPriorityTable(priorityProcesses);
                TableView<Process> sjfTable = createSJFTable(sjfProcesses);

                Label priorityTitle = new Label("Priority Scheduling");
                priorityTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                Label sjfTitle = new Label("SJF Scheduling");
                sjfTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                VBox priorityBox = new VBox(10);
                priorityBox.setAlignment(Pos.CENTER);
                priorityBox.getChildren().addAll(priorityTitle, priorityTable);

                VBox sjfBox = new VBox(10);
                sjfBox.setAlignment(Pos.CENTER);
                sjfBox.getChildren().addAll(sjfTitle, sjfTable);

                HBox tablesBox = new HBox(20);
                tablesBox.setAlignment(Pos.CENTER);
                tablesBox.getChildren().addAll(priorityBox, sjfBox);

                VBox root = new VBox(20);
                root.setPadding(new Insets(20));
                root.setAlignment(Pos.CENTER);

                Label mainTitle = new Label("Scheduling Comparison Result");
                mainTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

                root.getChildren().addAll(mainTitle, tablesBox);
                return root;
        }

        private TableView<Process> createPriorityTable(ArrayList<Process> processes) {
                TableView<Process> table = new TableView<>();

                TableColumn<Process, Integer> pidColumn = new TableColumn<>("PID");
                pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));

                TableColumn<Process, Integer> arrivalColumn = new TableColumn<>("Arrival");
                arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

                TableColumn<Process, Integer> burstColumn = new TableColumn<>("Burst");
                burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

                TableColumn<Process, Integer> priorityColumn = new TableColumn<>("Priority");
                priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

                TableColumn<Process, Integer> completionColumn = new TableColumn<>("Completion");
                completionColumn.setCellValueFactory(new PropertyValueFactory<>("completionTime"));

                TableColumn<Process, Integer> waitingColumn = new TableColumn<>("WT");
                waitingColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));

                TableColumn<Process, Integer> turnaroundColumn = new TableColumn<>("TAT");
                turnaroundColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));

                TableColumn<Process, Integer> responseColumn = new TableColumn<>("RT");
                responseColumn.setCellValueFactory(new PropertyValueFactory<>("responseTime"));

                table.getColumns().addAll(
                        pidColumn,
                        arrivalColumn,
                        burstColumn,
                        priorityColumn,
                        completionColumn,
                        waitingColumn,
                        turnaroundColumn,
                        responseColumn
                );

                table.setItems(FXCollections.observableArrayList(processes));
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setPrefWidth(570);
                table.setPrefHeight(450);

                return table;
        }

        private TableView<Process> createSJFTable(ArrayList<Process> processes) {
                TableView<Process> table = new TableView<>();

                TableColumn<Process, Integer> pidColumn = new TableColumn<>("PID");
                pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));

                TableColumn<Process, Integer> arrivalColumn = new TableColumn<>("Arrival");
                arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

                TableColumn<Process, Integer> burstColumn = new TableColumn<>("Burst");
                burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

                TableColumn<Process, Integer> completionColumn = new TableColumn<>("Completion");
                completionColumn.setCellValueFactory(new PropertyValueFactory<>("completionTime"));

                TableColumn<Process, Integer> waitingColumn = new TableColumn<>("WT");
                waitingColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));

                TableColumn<Process, Integer> turnaroundColumn = new TableColumn<>("TAT");
                turnaroundColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));

                TableColumn<Process, Integer> responseColumn = new TableColumn<>("RT");
                responseColumn.setCellValueFactory(new PropertyValueFactory<>("responseTime"));

                table.getColumns().addAll(
                        pidColumn,
                        arrivalColumn,
                        burstColumn,
                        completionColumn,
                        waitingColumn,
                        turnaroundColumn,
                        responseColumn
                );

                table.setItems(FXCollections.observableArrayList(processes));
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setPrefWidth(570);
                table.setPrefHeight(450);

                return table;
        }
}

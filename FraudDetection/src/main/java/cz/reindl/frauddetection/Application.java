package cz.reindl.frauddetection;

import cz.reindl.frauddetection.controller.Controller;
import cz.reindl.frauddetection.service.fetcher.DataConverter;
import cz.reindl.frauddetection.view.View;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public Pane root;
    public FileChooser fileChooser;
    private Controller controller;
    private View view;
    private DataConverter converter;
    private Button analyzeButton, generateButton, getFileButton;
    public Label fileLabel;

    @Override
    public void start(Stage stage) throws IOException {
        controller = new Controller(this, view, converter);
        view = new View(this);
        converter = new DataConverter(controller, this);

        generateButton = view.addButton(80, 410, "Generate Random Data");
        analyzeButton = view.addButton(340, 410, "Analyze");
        getFileButton = view.addButton(10, 350, "Get File");

        fileChooser = view.fileChooser();
        fileLabel = view.addLabel(15, 380, "Select file");

        getFileButton.setOnAction(event -> {
            controller.selectedFile = fileChooser.showOpenDialog(stage);
            if (controller.selectedFile != null) {
                fileLabel.setText(controller.selectedFile.getName() + " chosen");
                System.out.println(controller.selectedFile.getAbsolutePath());
            }
        });

        generateButton.setOnAction(event -> {
            try {
                controller.generateData(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        analyzeButton.setOnAction(event -> {
            try {
                if (controller.selectedFile != null) {
                    controller.analyzeData(event);
                    root.getChildren().add(controller.showGraph("Fraud graph", controller.fraudList));
                } else {
                    fileLabel.setText("No file selected yet");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        root = new Pane();
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view/view.fxml"));
        Scene scene = new Scene(root, 500, 450);
        root.getChildren().add(controller.showGraph("Benford's graph", controller.fraudList));
        //root.getChildren().add(controller.showGraph("Fraud graph", controller.fraudList));
        root.getChildren().add(generateButton);
        root.getChildren().add(analyzeButton);
        root.getChildren().add(getFileButton);
        root.getChildren().add(fileLabel);
        //Scene scene = new Scene(fxmlLoader.load(), 500, 450);

        stage.setTitle("Benford's law");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
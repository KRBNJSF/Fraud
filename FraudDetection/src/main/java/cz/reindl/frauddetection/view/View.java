package cz.reindl.frauddetection.view;

import cz.reindl.frauddetection.Application;
import cz.reindl.frauddetection.service.conf.Settings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class View {

    Application application;

    public View(Application application) {
        this.application = application;
    }

    public Button addButton(int x, int y, String text) {
        Button button = new Button();
        button.setText(text);
        button.setMinWidth(button.getWidth());
        button.setTranslateX(x);
        button.setTranslateY(y);
        button.setOnMouseEntered(e -> button.setMinWidth(button.getWidth() * 1.2));
        button.setOnMouseExited(e -> button.setMinWidth(button.getWidth() / 1.2));
        return button;
    }

    public Label addLabel(int x, int y, String text) {
        Label label = new Label();
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setText(text);
        return label;
    }

    public FileChooser fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Settings.PREFIX));
        fileChooser.setTitle("Select a file");
        return fileChooser;
    }

}

package cz.reindl.frauddetection.controller;

import cz.reindl.frauddetection.Application;
import cz.reindl.frauddetection.service.conf.Settings;
import cz.reindl.frauddetection.service.fetcher.DataConverter;
import cz.reindl.frauddetection.utils.Utils;
import cz.reindl.frauddetection.view.View;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    public List<Double> fraudList = new LinkedList<>();
    public File selectedFile;

    public Button analyzeButton;
    public LineChart lineChart;
    public AnchorPane anchorPane;
    public NumberAxis xAxis;
    public NumberAxis yAxis;

    Application application;
    View view;
    DataConverter converter;

    public Controller(Application application, View view, DataConverter converter) {
        this.application = application;
        this.view = view;
        this.converter = converter;
        setGraph();
        fraudList.add(0.0);
        fraudList.add(30.1);
        fraudList.add(17.6);
        fraudList.add(12.5);
        fraudList.add(9.7);
        fraudList.add(7.9);
        fraudList.add(6.7);
        fraudList.add(5.8);
        fraudList.add(5.1);
        fraudList.add(4.6);
    }

    public void analyzeData(ActionEvent actionEvent) throws IOException {
        if (selectedFile != null) {
            DataConverter.convertData(selectedFile.getAbsolutePath());
            //converter.getFirst();
        } else {
            application.fileLabel.setText("No File Selected yet");
        }
    }

    public void generateData(ActionEvent actionEvent) throws IOException {
        DataConverter.generateData(Settings.PREFIX + "random.csv");
    }

    public void setGraph() {
        yAxis = new NumberAxis(0, 100, 10);
        yAxis.setLabel("Percentage");

        xAxis = new NumberAxis(1, 9, 1);
        xAxis.setLabel("Numbers");

        lineChart = new LineChart(xAxis, yAxis);
    }

    public Pane showGraph(String name, List<Double> percentage) {
        for (int i = 0; i < Settings.VALUES; i++) {
            fraudList.add(1.0 + (9.0 - 1.0) * Utils.randomNum());
        }
        XYChart.Series series = new XYChart.Series();
        series.setName(name);

        for (int i = 0; i < percentage.size(); i++) {
            series.getData().add(new XYChart.Data<>(i, percentage.get(i)));
        }

        lineChart.getData().add(series);
        fraudList.clear();
        return new Pane(lineChart);
    }

}
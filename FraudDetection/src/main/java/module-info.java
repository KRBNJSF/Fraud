module cz.reindl.frauddetection {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.dataformat.csv;


    opens cz.reindl.frauddetection to javafx.fxml;
    exports cz.reindl.frauddetection;
    exports cz.reindl.frauddetection.service.conf;
    exports cz.reindl.frauddetection.controller;
    opens cz.reindl.frauddetection.controller to javafx.fxml;
    exports cz.reindl.frauddetection.service.fetcher;
    opens cz.reindl.frauddetection.service.fetcher to javafx.fxml;
}
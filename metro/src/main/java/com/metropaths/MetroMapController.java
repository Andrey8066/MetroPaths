package com.metropaths;

import java.sql.SQLException;

import javax.xml.transform.stax.StAXResult;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MetroMapController {
    @FXML
    protected Pane metroPathsVBox;

    protected Stations stations;
    protected Lines lines;
    protected Connections connections;
    protected DatabaseHandler databaseHandler;
    protected PathFinder pathFinder;

    protected Station startStation = null;
    protected Station finishStation = null;

    protected Integer buttonSize = 14;

    @FXML
    public void initialize() throws Exception {
        this.databaseHandler = new DatabaseHandler("jdbc:postgresql://127.0.0.1:5432/metropaths", "postgres", "123456");
        this.stations = new Stations(databaseHandler);
        this.connections = new Connections(databaseHandler);
        this.pathFinder = new PathFinder(stations, connections);
        this.lines = new Lines(databaseHandler);
        drawMap();

    }

    protected void drawMap() throws Exception {

        for (Station station : stations.GetAll()) {
            DrawStation(station);
        }

        for (Connection connection : connections.GetAll()) {
            DrawConnection(connection);
        }
    }

    protected void DrawStation(Station station) throws SQLException {

        Button stationButton = new Button();
        Label stationLabel = new Label();
        Circle stationCircle = new Circle(station.getStationX() + buttonSize, station.getStationY() + buttonSize,
                buttonSize, Color.web(lines.getColorById(station.getLineId())));

        stationButton.setLayoutX(station.getStationX());
        stationButton.setLayoutY(station.getStationY());
        stationButton.setMinSize(buttonSize * 2, buttonSize * 2);
        stationButton.setMaxSize(buttonSize * 2, buttonSize * 2);
        stationButton.setShape(new Circle(buttonSize));
        stationButton.setOpacity(0);
        stationButton.hoverProperty().addListener((obs, oldVal, isHovering) -> {
            if (isHovering) {
                stationButton.setOpacity(0.3);
            } else {
                stationButton.setOpacity(0);
            }
        });
        stationButton.setOnAction(e -> onClick(station));

        stationLabel.setText(station.getStationName());
        stationLabel.setLayoutX(station.getTextX());
        stationLabel.setLayoutY(station.getTextY());

        this.metroPathsVBox.getChildren().addAll(stationCircle, stationLabel, stationButton);
    }

    protected void DrawConnection(Connection connection) throws SQLException {
        Line line = new Line();

        line.setStartX(connection.getStation1().getStationX() + buttonSize);
        line.setStartY(connection.getStation1().getStationY() + buttonSize);

        line.setEndX(connection.getStation2().getStationX() + buttonSize);
        line.setEndY(connection.getStation2().getStationY() + buttonSize);

        line.setStrokeWidth(5);

        line.setStroke(Color.web(lines.getColorById(connection.getStation1().getLineId())));

        this.metroPathsVBox.getChildren().add(line);
    }

    protected void onClick(Station station) {

        try {

            if (station.isTransfer()) {
                System.out.println(1);
                Stage stage = new Stage();
                VBox stationSelectionVBox = new VBox();
                for (Station s : stations.geStationsByCoords(station.getStationX(), station.getStationY())) {
                    Button stationChooseButton = new Button();
                    HBox hBox = new HBox();

                    hBox.setPadding(new Insets(10));
                    hBox.setSpacing(10);
                    hBox.getChildren().add(new Circle(buttonSize, Color.web(lines.getColorById(s.getLineId()))));
                    hBox.getChildren().add(new Label(s.getStationName()));

                    stationChooseButton.setGraphic(hBox);
                    stationChooseButton.setOnAction(e -> {
                        chooseStation(s);
                        stage.close();
                    });

                    stationSelectionVBox.getChildren().add(stationChooseButton);
                }
                stage.setScene(new Scene(stationSelectionVBox));
                stage.show();

            } else {
                chooseStation(station);
            }
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }

    protected void chooseStation(Station station) {

        if (startStation == null)
            startStation = station;
        else {
            finishStation = station;
            System.out.print(pathFinder.getPath(startStation.getStationId(), finishStation.getStationId()).lessTime);
            System.out.print(pathFinder.getPath(startStation.getStationId(), finishStation.getStationId()).path);
            
            startStation = null;
        }
    }

}

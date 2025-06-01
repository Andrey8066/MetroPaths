package com.metropaths;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

    protected Station startStation = null;
    protected Station finishStation = null;

    protected Integer buttonSize = 7;

    @FXML
    public void initialize() throws Exception {
        this.databaseHandler = new DatabaseHandler("jdbc:postgresql://127.0.0.1:5432/metropaths", "postgres", "123456");
        this.stations = new Stations(databaseHandler);
        this.connections = new Connections(databaseHandler);
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

        stationButton.setLayoutX(station.getStationX());
        stationButton.setLayoutY(station.getStationY());
        stationButton.setMinSize(buttonSize*4,buttonSize*4);
        stationButton.setMaxSize(buttonSize*4, buttonSize*4);
        stationButton.setShape(new Circle(buttonSize, Color.web(lines.getColorById(station.getLineId()))));
        stationButton.setStyle("-fx-background-color: " + lines.getColorById(station.getLineId()) + " ;");
        stationButton.setOnAction(e -> onClick(station));

        stationLabel.setText(station.getStationName());
        stationLabel.setLayoutX(station.getStationX());
        stationLabel.setLayoutY(station.getStationY());

        this.metroPathsVBox.getChildren().addAll(stationButton, stationLabel);
    }

    protected void DrawConnection(Connection connection) throws SQLException {
        Line line = new Line();

        line.setStartX(connection.getStation1().getStationX() + 5);
        line.setStartY(connection.getStation1().getStationY() + 5);

        line.setEndX(connection.getStation2().getStationX() + 5);
        line.setEndY(connection.getStation2().getStationY() + 5);

        line.setStrokeWidth(5);

        line.setStroke(Color.web(lines.getColorById(connection.getStation1().getLineId())));

        this.metroPathsVBox.getChildren().add(line);
    }

    protected void onClick(Station station){
        
        try {
            
        if (station.isTransfer()) {
            System.out.println(1);
            Stage stage = new Stage();
            VBox stationSelectionVBox  = new VBox();
            for (Station s: stations.geStationsByCoords(station.getStationX(), station.getStationY()))
            {
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
        }}
        catch (Exception exception){
            System.err.println(exception);
        }
    }

    protected void chooseStation(Station station){
        if (startStation == null)
                startStation = station;
            else
                finishStation = station;
    }

    
}

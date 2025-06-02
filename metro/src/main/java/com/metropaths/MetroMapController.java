package com.metropaths;

import java.sql.SQLException;

import javax.xml.transform.stax.StAXResult;

import javafx.application.Platform;
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
import javafx.scene.Node;

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

    protected Double buttonSize = 14.0;
    protected Double widthCoefficient = 1.0;
    protected Double heightCoefficient = 1.0;

    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialTranslateX;
    private double initialTranslateY;

    @FXML
    private Label startStationLabel;
    
    @FXML
    private Label finishStationLabel;

    @FXML
    public void initialize() throws Exception {
        this.databaseHandler = new DatabaseHandler("jdbc:postgresql://127.0.0.1:5432/metropaths", "postgres", "123456");
        this.stations = new Stations(databaseHandler);
        this.connections = new Connections(databaseHandler);
        this.pathFinder = new PathFinder(stations, connections);
        this.lines = new Lines(databaseHandler);
        drawMap();

        Platform.runLater(() -> {

            this.widthCoefficient = metroPathsVBox.getScene().getWidth() / 1600;
            this.heightCoefficient = metroPathsVBox.getScene().getHeight() / 1200;
            this.buttonSize = this.buttonSize * this.widthCoefficient;
            try {
                drawMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        metroPathsVBox.setOnMousePressed(event -> {
            mouseAnchorX = event.getSceneX();
            mouseAnchorY = event.getSceneY();
        });

        metroPathsVBox.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseAnchorX;
            double deltaY = event.getSceneY() - mouseAnchorY;

            metroPathsVBox.setLayoutX(metroPathsVBox.getLayoutX() + deltaX);
            metroPathsVBox.setLayoutY(metroPathsVBox.getLayoutY() + deltaY);

            mouseAnchorX = event.getSceneX();
            mouseAnchorY = event.getSceneY();
        });
        
        metroPathsVBox.setOnScroll(event -> {
            if (event.isControlDown()) { // или event.isInertia() для тачпада
                double delta = event.getDeltaY();

                double scaleFactor = 1.0;
                if (delta > 0) {
                    scaleFactor = 1.1;
                } else if (delta < 0) {
                    scaleFactor = 0.9;
                }

                double newScaleX = metroPathsVBox.getScaleX() * scaleFactor;
                double newScaleY = metroPathsVBox.getScaleY() * scaleFactor;

                newScaleX = Math.min(Math.max(newScaleX, 0.5), 3.0);
                newScaleY = Math.min(Math.max(newScaleY, 0.5), 3.0);

                metroPathsVBox.setScaleX(newScaleX);
                metroPathsVBox.setScaleY(newScaleY);

                event.consume();
            }
        });
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
        Circle stationCircle = new Circle((station.getStationX() + buttonSize) * widthCoefficient,
                (station.getStationY() + buttonSize) * heightCoefficient,
                buttonSize, Color.web(lines.getColorById(station.getLineId())));

        stationButton.setLayoutX(station.getStationX() * widthCoefficient);
        stationButton.setLayoutY(station.getStationY() * heightCoefficient);
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
        stationButton.setOnAction(e -> {
            try {
                onClick(station);
                this.metroPathsVBox.getChildren()
                        .add(new Circle((station.getStationX() + buttonSize) * widthCoefficient,
                                (station.getStationY() + buttonSize) * heightCoefficient,
                                buttonSize, Color.AQUAMARINE));
            } catch (Exception exception) {
                System.out.println(exception);
            }
        });

        stationLabel.setText(station.getStationName());
        stationLabel.setLayoutX(station.getTextX() * widthCoefficient);
        stationLabel.setLayoutY(station.getTextY() * heightCoefficient);

        this.metroPathsVBox.getChildren().addAll(stationCircle, stationLabel, stationButton);
    }

    protected void DrawConnection(Connection connection) throws SQLException {
        Line line = new Line();

        line.setStartX((connection.getStation1().getStationX() + buttonSize) * widthCoefficient);
        line.setStartY((connection.getStation1().getStationY() + buttonSize) * heightCoefficient);

        line.setEndX((connection.getStation2().getStationX() + buttonSize) * widthCoefficient);
        line.setEndY((connection.getStation2().getStationY() + buttonSize) * heightCoefficient);

        line.setStrokeWidth(5);

        line.setStroke(Color.web(lines.getColorById(connection.getStation1().getLineId())));

        this.metroPathsVBox.getChildren().add(line);
    }

    protected void DrawPath(Path path) throws SQLException {
        for (Node n : metroPathsVBox.getChildren()) {
            n.setOpacity(0.3);
        }
        
        for (int i = 0; i < path.path.size() - 1; i++) {
            System.out.println(path.path.get(i));
            Connection connection = connections.getConnectionByStations(path.path.get(i), path.path.get(i + 1));
            System.out.println(path.path.get(i));
            if (!connection.isTransfer()) {
                System.out.println(path.path);
                DrawConnection(connection);
                DrawStation(connection.station1);
            }
            else {
                DrawStation(connection.station1);
            }
        }
    }

    protected void onClick(Station station) throws SQLException {

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
                        try {
                            chooseStation(s);
                            stage.close();
                        } catch (Exception exception) {
                            System.out.println(exception);
                        }
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

    @FXML
    private void onResetClick() {
        startStation = null;
        finishStation = null;
        startStationLabel.setText("Отправная: -");
        finishStationLabel.setText("Конечная: -");
    }

    protected void chooseStation(Station station) {
        if (startStation == null) {
            startStation = station;
            startStationLabel.setText("Отправная: " + station.getStationName());
        } else {
            finishStation = station;
            finishStationLabel.setText("Конечная: " + station.getStationName());

            System.out.print(pathFinder.getPath(startStation.getStationId(), finishStation.getStationId()).lessTime);
            System.out.print(pathFinder.getPath(startStation.getStationId(), finishStation.getStationId()).path);

            startStation = null;
        }
    }


}

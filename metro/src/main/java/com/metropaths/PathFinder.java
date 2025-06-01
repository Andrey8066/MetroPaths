package com.metropaths;

import java.sql.SQLException;

public class PathFinder {
    protected Stations stations;
    protected Connections connections;
    public PathFinder(){
        this.stations = stations;
        this.connections = connections;
    }

    public void createMatrix() throws SQLException{
        double[][] paths = new double[this.stations.getNumberofColumns()][this.stations.getNumberofColumns()];
        for (Connection conn : connections.GetAll()){
            paths[conn.getStation1().getStationId()][conn.getStation2().getStationId()] = conn.getTransferTime();
            paths[conn.getStation2().getStationId()][conn.getStation1().getStationId()] = conn.getTransferTime();
        }
    }

}

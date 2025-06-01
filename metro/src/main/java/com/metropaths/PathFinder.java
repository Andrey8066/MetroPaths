package com.metropaths;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class PathFinder {
    protected Stations stations;
    protected Connections connections;
    protected double[][] paths;

    protected Boolean pathSearchingParam = true;
    protected Integer numberofStations;
    public PathFinder(Stations stations, Connections connections) throws SQLException{
        this.stations = stations;
        this.connections = connections;

        createMatrix();
    }

    public void searchLessTime(Boolean bool){
        pathSearchingParam = bool;
    }

    public void createMatrix() throws SQLException{
        this.numberofStations = this.stations.getNumberofColumns();
        this.paths = new double[this.numberofStations][this.numberofStations];
        
        for (int i = 0; i < this.numberofStations; i++){
            for (int j = 0; j < this.numberofStations; j++){
                paths[i][j] = Double.MAX_VALUE;
            }
        }
        for (Connection conn : connections.GetAll()){
            this.paths[conn.getStation1().getStationId()-1][conn.getStation2().getStationId()-1] = conn.getTransferTime();
            this.paths[conn.getStation2().getStationId()-1][conn.getStation1().getStationId()-1] = conn.getTransferTime();
        }
    }

    public Path getPath(Integer id1, Integer id2){
        if (this.pathSearchingParam){
            return dijkstra( id1-1, id2-1);
        }
        else{
            return null;
        }
    }

    public Path dijkstra(int startStation, int endStation) {
        double[] time = new double[this.numberofStations];
        Arrays.fill(time, Double.POSITIVE_INFINITY);
        int[] previous = new int[this.numberofStations];
        Arrays.fill(previous, -1);
        boolean[] visited = new boolean[this.numberofStations];
        
        time[startStation] = 0.0;
    
        for (int i = 0; i < this.numberofStations; i++) {
            int minVertex = findMinVertex(time, visited);
            if (minVertex == -1 || minVertex == endStation) break;
            
            visited[minVertex] = true;
    
            for (int j = 0; j < this.numberofStations; j++) {
                if (!visited[j] && paths[minVertex][j] < Double.POSITIVE_INFINITY) {
                    double newTime = time[minVertex] + paths[minVertex][j];
                    if (newTime < time[j]) {
                        time[j] = newTime;
                        previous[j] = minVertex;
                    }
                }
            }
        }
    
        if (time[endStation] == Double.POSITIVE_INFINITY) {
            return null; // Путь не найден
        }
    
        List<Integer> path = new ArrayList<>();
        for (int at = endStation; at != -1; at = previous[at]) {
            path.add(at + 1); // +1 чтобы вернуть исходные ID станций
        }
        Collections.reverse(path);
    
        return new Path(time[endStation], path);
    }

    private int findMinVertex(double[] time, boolean[] visited) {
        int minVertex = -1;
        double minTime = Double.POSITIVE_INFINITY;
        
        for (int i = 0; i < time.length; i++) {
            if (!visited[i] && time[i] < minTime) {
                minTime = time[i];
                minVertex = i;
            }
        }
        return minVertex;
    }

}

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
    protected double[][] transfers;
    protected Boolean[][] adjacency;

    protected Boolean pathSearchingParam = true;
    protected Integer numberofStations;

    public PathFinder(Stations stations, Connections connections) throws SQLException {
        this.stations = stations;
        this.connections = connections;

        createMatrix();
    }

    public void searchLessTime(Boolean bool) {
        pathSearchingParam = bool;
    }

    public void createMatrix() throws SQLException {
        this.numberofStations = this.stations.getNumberofColumns();
        this.paths = new double[this.numberofStations][this.numberofStations];
        this.adjacency = new Boolean[this.numberofStations][this.numberofStations];
        this.transfers = new double[this.numberofStations][this.numberofStations];

        for (int i = 0; i < this.numberofStations; i++) {
            for (int j = 0; j < this.numberofStations; j++) {
                paths[i][j] = Double.MAX_VALUE;
                adjacency[i][j] = false;

                transfers[i][j] = 0.0;
            }
        }

        for (Connection conn : connections.GetAll()) {
            int i = conn.getStation1().getStationId() - 1;
            int j = conn.getStation2().getStationId() - 1;
            double time = conn.getTransferTime();

            paths[i][j] = time;
            paths[j][i] = time;
            adjacency[i][j] = true;
            adjacency[j][i] = true;
            transfers[i][j] = (conn.getStation1().getLineId() == conn.getStation2().getLineId()) ? 0.0 : 1.0;;
            transfers[j][i] = 0.0;
        }


    }

    public Path getPath(Integer id1, Integer id2) throws SQLException{
        if (this.pathSearchingParam) {
            return dijkstra(id1 - 1, id2 - 1);
        } else {
            return minTransfersPath(id1 - 1, id2 - 1);
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
            if (minVertex == -1 || minVertex == endStation)
                break;

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

    public Path dijkstraMinTransfers(int startStation, int endStation) {
        double[] transfer = new double[this.numberofStations];
        Arrays.fill(transfer, Double.POSITIVE_INFINITY);
        int[] previous = new int[this.numberofStations];
        Arrays.fill(previous, -1);
        boolean[] visited = new boolean[this.numberofStations];

        transfer[startStation] = 0.0;

        for (int i = 0; i < this.numberofStations; i++) {
            int minVertex = findMinVertex(transfer, visited);
            if (minVertex == -1 || minVertex == endStation)
                break;

            visited[minVertex] = true;

            for (int j = 0; j < this.numberofStations; j++) {
                if (!visited[j] && paths[minVertex][j] < Double.POSITIVE_INFINITY) {
                    System.out.println(transfer[minVertex]);
                    System.out.println(transfers[minVertex][j]);
                    double newTime = transfer[minVertex] + transfers[minVertex][j];
                    if (newTime < transfer[j]) {
                        transfer[j] = newTime;
                        previous[j] = minVertex;
                    }
                }
            }
        }

        if (transfer[endStation] == Double.POSITIVE_INFINITY) {
            return null; // Путь не найден
        }

        List<Integer> path = new ArrayList<>();
        for (int at = endStation; at != -1; at = previous[at]) {
            path.add(at + 1); // +1 чтобы вернуть исходные ID станций
        }
        Collections.reverse(path);

        return new Path(transfer[endStation], path);
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

    public Path minTransfersPath(int startStation, int endStation) throws SQLException {
        int[] transfers = new int[this.numberofStations];
        Arrays.fill(transfers, Integer.MAX_VALUE);
        int[] previous = new int[this.numberofStations];
        Arrays.fill(previous, -1);
        boolean[] visited = new boolean[this.numberofStations];
        Integer[] lines = new Integer[this.numberofStations];
        System.out.println(1);
        for (int i = 0; i < numberofStations; i++) {
            lines[i] = stations.GetById(Integer.toString(i + 1)).getLineId(); // предполагаем, что getLineId()
                                                                              // существует
        }

        transfers[startStation] = 0;

        for (int i = 0; i < this.numberofStations; i++) {
            int minVertex = findMinVertexTransfers(transfers, visited);
            if (minVertex == -1 || minVertex == endStation)
                break;

            visited[minVertex] = true;

            for (int j = 0; j < this.numberofStations; j++) {
                if (!visited[j] && adjacency[minVertex][j]) {
                    int addTransfer = (lines[minVertex].equals(lines[j])) ? 0 : 1;
                    int newTransfers = transfers[minVertex] + addTransfer;

                    if (newTransfers < transfers[j]) {
                        transfers[j] = newTransfers;
                        previous[j] = minVertex;
                    }
                }
            }
        }

        if (transfers[endStation] == Integer.MAX_VALUE) {
            return null;
        }

        List<Integer> path = new ArrayList<>();
        for (int at = endStation; at != -1; at = previous[at]) {
            path.add(at + 1); // +1 чтобы вернуть оригинальный ID
        }
        Collections.reverse(path);

        return new Path((double) transfers[endStation], path);
    }

    private int findMinVertexTransfers(int[] transfers, boolean[] visited) {
        int minVertex = -1;
        int minTransfers = Integer.MAX_VALUE;

        for (int i = 0; i < transfers.length; i++) {
            if (!visited[i] && transfers[i] < minTransfers) {
                minTransfers = transfers[i];
                minVertex = i;
            }
        }
        return minVertex;
    }

    public void setPathSearchingParam() throws Exception {
        this.pathSearchingParam = !this.pathSearchingParam;
    }

    public boolean getPathSearchingParam(){
        return this.pathSearchingParam;
    }
}

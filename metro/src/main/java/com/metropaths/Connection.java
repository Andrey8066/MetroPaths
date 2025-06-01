package com.metropaths;

import java.sql.SQLException;

public class Connection {
    protected Integer id;
    protected Station station1;
    protected Station station2;
    protected boolean isTransfer;
    protected Double transferTime;

    protected Stations stations;

    // Конструктор по умолчанию
    public Connection() {
    }

    // Конструктор со всеми параметрами
    public Connection(Integer id, Integer station1, Integer station2, boolean isTransfer, double transferTime ,DatabaseHandler databaseHandler) throws SQLException{
        this.id = id;
        this.station1 = stations.GetById(Integer.toString(station1));
        this.station2 = stations.GetById(Integer.toString(station2));
        this.isTransfer = isTransfer;
        this.transferTime =  transferTime;
    }

    public Connection(String[] data, DatabaseHandler databaseHandler) throws SQLException{
        if (data == null || data.length < 5) {
            throw new IllegalArgumentException("Invalid input array");
        }
    this.stations = new Stations(databaseHandler);
        this.id = Integer.parseInt(data[0]);
        this.station1 = stations.GetById(data[1]);
        this.station2 = stations.GetById(data[2]);
        this.isTransfer = ((int) data[3].charAt(0) == 116);
        this.transferTime =  Double.parseDouble(data[4]);
    }

     // Геттеры
     public Integer getId() {
        return id;
    }

    public Station getStation1() {
        return station1;
    }

    public Station getStation2() {
        return station2;
    }

    public boolean isTransfer() {
        return isTransfer;
    }

    public Double getTransferTime() {
        return transferTime;
    }

    public Stations getStations() {
        return stations;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }

    public void setStation1(Station station1) {
        this.station1 = station1;
    }

    public void setStation2(Station station2) {
        this.station2 = station2;
    }

    public void setTransfer(boolean transfer) {
        isTransfer = transfer;
    }

    public void setTransferTime(Double transferTime) {
        this.transferTime = transferTime;
    }

    public void setStations(Stations stations) {
        this.stations = stations;
    }
}
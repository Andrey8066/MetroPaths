package com.metropaths;

public class Station {
    protected String stationName;
    protected Integer stationId;
    protected Integer lineId;
    protected Integer stationX;
    protected Integer stationY;
    protected Integer textX;
    protected Integer textY;
    protected Boolean isTransfer;

    // Конструктор со всеми параметрами
    public Station(Integer stationId, String stationName, Integer lineId, 
                  Integer stationX, Integer stationY, Integer textX, Integer textY, Boolean isTransfer) {
        this.stationName = stationName;
        this.stationId = stationId;
        this.lineId = lineId;
        this.stationX = stationX;
        this.stationY = stationY;
        this.textX = textX;
        this.textY = textY;
        this.isTransfer = isTransfer;
    }

    public Station(String[] data) {
        if (data == null || data.length < 8) {
            throw new IllegalArgumentException("Invalid input array");
        }
        
        this.stationId = Integer.parseInt(data[0]);
        this.stationName = data[1];
        this.lineId = Integer.parseInt(data[2]);
        this.stationX = Integer.parseInt(data[3]);
        this.stationY = Integer.parseInt(data[4]);
        this.textX = Integer.parseInt(data[5]);
        this.textY = Integer.parseInt(data[6]);
        this.isTransfer = !((int) data[3].charAt(0) == 116);
    }


    // Геттеры для всех полей
    public String getStationName() {
        return stationName;
    }

    public Integer getStationId() {
        return stationId;
    }

    public Integer getLineId() {
        return lineId;
    }

    public Integer getStationX() {
        return stationX;
    }

    public Integer getStationY() {
        return stationY;
    }

    public Integer getTextX() {
        return textX;
    }

    public Integer getTextY() {
        return textY;
    }

    public Boolean isTransfer() {
        return isTransfer;
    }
}

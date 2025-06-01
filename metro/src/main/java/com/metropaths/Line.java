package com.metropaths;

public class Line {
    protected String lineName;
    protected Integer lineId;
    protected String color;

    // Конструктор по умолчанию
    public Line() {
    }

    // Конструктор со всеми параметрами
    public Line(Integer lineId, String lineName, String color) {
        this.lineName = lineName;
        this.lineId = lineId;
        this.color = color;
    }

    public Line(String[] data) {
        if (data == null || data.length < 3) {
            throw new IllegalArgumentException("Invalid input array");
        }
        
        this.lineId = Integer.parseInt(data[0]);
        this.lineName = data[1];
        this.color = data[2];
    }

    // Геттеры
    public String getLineName() {
        return lineName;
    }

    public Integer getLineId() {
        return lineId;
    }

    public String getColor() {
        return color;
    }

    // Сеттеры
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
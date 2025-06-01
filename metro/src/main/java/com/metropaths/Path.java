package com.metropaths;

import java.util.List;

public class Path {
    public Double lessTime;
    public List<Integer> path;

    public Path (Double lessTime, List<Integer> path) {
        this.lessTime = lessTime;
        this.path = path;
    }
}

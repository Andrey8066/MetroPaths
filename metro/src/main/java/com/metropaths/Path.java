package com.metropaths;

import java.util.List;

public class Path {
    public int lessTime;
    public List<Integer> path;

    public Path (Integer lessTime, List<Integer> path) {
        this.lessTime = lessTime;
        this.path = path;
    }
}

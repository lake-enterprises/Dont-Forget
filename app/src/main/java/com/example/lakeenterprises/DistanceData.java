package com.example.lakeenterprises;

public class DistanceData {
    private double distance;

    /**
     * creates a DistanceData object by pulling information from Firebase Database
     * @param dist the distance from the walker at a given time
     */
    public DistanceData(double dist){
        distance=dist;
    }

    public double getDistance(){
        return distance;
    }



}

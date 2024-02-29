package com.example.hw224a10357.Model;

public class Player {

    private String name;
    private int points;
    private double latitude;
    private double longitude;

    public Player(String name, int points, double latitude, double longitude) {
        this.name = name;
        this.points = points;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Player(){
        this.name = "";
        this.points = 0;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }
    // TODO: Map Position


    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

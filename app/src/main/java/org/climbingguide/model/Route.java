package org.climbingguide.model;

import android.app.Activity;

public class Route extends Activity {

    private int id;
    private String name;
    private int idOfSector;
    private String difficulty;
    private int bolts;
    private int length;

    private double latitude;
    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdOfSector() {
        return idOfSector;
    }

    public void setIdOfSector(int idOfSector) {
        this.idOfSector = idOfSector;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getBolts() {
        return bolts;
    }

    public void setBolts(int bolts) {
        this.bolts = bolts;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return name;
    }
}

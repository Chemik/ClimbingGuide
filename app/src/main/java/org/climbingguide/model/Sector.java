package org.climbingguide.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class Sector extends Activity {

    private int id;
    private String name;
    private int idOfArea;
    private List<Route> routes = new ArrayList<Route>();

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

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

    public int getIdOfArea() {
        return idOfArea;
    }

    public void setIdOfArea(int idOfArea) {
        this.idOfArea = idOfArea;
    }

    @Override
    public String toString() {
        return name;
    }
}

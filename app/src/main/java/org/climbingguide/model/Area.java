package org.climbingguide.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class Area extends Activity {

    private int id;
    private String name;
    private List<Sector> sectors = new ArrayList<Sector>();

    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
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

    @Override
    public String toString() {
        return name;
    }
}

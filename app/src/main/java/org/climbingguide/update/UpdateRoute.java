package org.climbingguide.update;

import org.climbingguide.dao.RouteDao;
import org.climbingguide.model.Route;

import android.content.Context;

public class UpdateRoute {

    public void updateRoute(int id, String name, int id_of_sector, String dificulty, int bolts, int length, double latitute, double longitude, Context context) {
        Route route = new Route();

        route.setId(id);
        route.setName(name);
        route.setIdOfSector(id_of_sector);
        route.setDifficulty(dificulty);
        route.setBolts(bolts);
        route.setLength(length);
        route.setLatitude(latitute);
        route.setLongitude(longitude);

        RouteDao daor = new RouteDao(context);
        daor.open();
        daor.addRoute(route);
        daor.close();
    }
}

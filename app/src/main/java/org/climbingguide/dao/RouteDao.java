package org.climbingguide.dao;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.model.Route;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RouteDao {

    private static final String LOG = RouteDao.class.getName();

    private SQLiteDatabase db;
    private SQLHelper dbHelperRoute;

    public RouteDao(Context context) {
        dbHelperRoute = new SQLHelper(context);
    }

    public void open() {
        db = dbHelperRoute.getWritableDatabase();
    }

    public void close() {
        dbHelperRoute.close();
    }

    //------------GET-ALL-ROUTES----------------------------------------------------------------------------------------------
    public List<Route> getAllRoutes() {
        List<Route> routeList = new ArrayList<Route>();

        String selectQuery = " SELECT * FROM " + SQLHelper.TABLE_ROUTE;
        Log.i(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Route route = new Route();

                route.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_ROUTE)));
                route.setName(c.getString(c.getColumnIndex(SQLHelper.ROUTE_NAME)));
                route.setIdOfSector(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_SECTOR)));
                route.setDifficulty(c.getString(c.getColumnIndex(SQLHelper.DIFFICULTY)));
                route.setBolts(c.getInt(c.getColumnIndex(SQLHelper.BOLTS)));
                route.setLength(c.getInt(c.getColumnIndex(SQLHelper.LENGTH)));
                route.setLatitude(c.getDouble(c.getColumnIndex(SQLHelper.LATITUTE)));
                route.setLongitude(c.getDouble(c.getColumnIndex(SQLHelper.LONGITUDE)));

                routeList.add(route);

            } while (c.moveToNext());
        }
        c.close();
        return routeList;
    }

    //----------GET--BY--ID----ROUTES-----------------------------------------------------------------------------------------
    public List<Route> getRoute(int find) {

        List<Route> routeList = new ArrayList<Route>();
        String selectQuery = " SELECT * FROM " + SQLHelper.TABLE_ROUTE + " WHERE " + SQLHelper.ID_OF_SECTOR + " = " + "'" + find + "'";
        Log.i(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Route route = new Route();

                route.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_ROUTE)));
                route.setName(c.getString(c.getColumnIndex(SQLHelper.ROUTE_NAME)));
                route.setIdOfSector(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_SECTOR)));
                route.setDifficulty(c.getString(c.getColumnIndex(SQLHelper.DIFFICULTY)));
                route.setBolts(c.getInt(c.getColumnIndex(SQLHelper.BOLTS)));
                route.setLength(c.getInt(c.getColumnIndex(SQLHelper.LENGTH)));
                route.setLatitude(c.getDouble(c.getColumnIndex(SQLHelper.LATITUTE)));
                route.setLongitude(c.getDouble(c.getColumnIndex(SQLHelper.LONGITUDE)));

                routeList.add(route);
            } while (c.moveToNext());
        }
        c.close();
        return routeList;
    }

    //-----------ADD-ROUTE----------------------------------------------------------------------------------------------------
    public void addRoute(Route route) {

        ContentValues value = new ContentValues();

        value.put(SQLHelper.ROUTE_NAME, route.getName());
        value.put(SQLHelper.ID_OF_SECTOR, route.getIdOfSector());
        value.put(SQLHelper.DIFFICULTY, route.getDifficulty());
        value.put(SQLHelper.BOLTS, route.getBolts());
        value.put(SQLHelper.LENGTH, route.getLength());
        value.put(SQLHelper.LATITUTE, route.getLatitude());
        value.put(SQLHelper.LONGITUDE, route.getLongitude());

        Log.i(LOG, "Inster to table route -->" + value);

        db.insert(SQLHelper.TABLE_ROUTE, null, value);
        db.close();
    }

    //-----------------SEARCH---------------------------------------------------------------------------------------------------
    public List<Route> routeSearch(String query) {
        List<Route> routeList = new ArrayList<Route>();
        String searchQuery = query + "%";
        String selectQuery;

        selectQuery = " SELECT * FROM " + SQLHelper.TABLE_ROUTE + " WHERE " + SQLHelper.ROUTE_NAME + " LIKE " + "'" + searchQuery + "'";

        Log.i(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Route route = new Route();

                route.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_ROUTE)));
                route.setName(c.getString(c.getColumnIndex(SQLHelper.ROUTE_NAME)));
                route.setIdOfSector(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_SECTOR)));
                route.setDifficulty(c.getString(c.getColumnIndex(SQLHelper.DIFFICULTY)));
                route.setBolts(c.getInt(c.getColumnIndex(SQLHelper.BOLTS)));
                route.setLength(c.getInt(c.getColumnIndex(SQLHelper.LENGTH)));
                route.setLatitude(c.getDouble(c.getColumnIndex(SQLHelper.LATITUTE)));
                route.setLongitude(c.getDouble(c.getColumnIndex(SQLHelper.LONGITUDE)));

                routeList.add(route);
            } while (c.moveToNext());
        }
        c.close();
        return routeList;
    }

    //----------------SEARCH---ID-OF-SECTOR-----------------------------------------------------------------------------------------------
    public List<Route> routeSearchId(String query, int idOfSector) {
        List<Route> routeList = new ArrayList<Route>();
        String searchQuery = query + "%";
        String selectQuery;

        selectQuery = " SELECT * FROM " + SQLHelper.TABLE_ROUTE + " WHERE " + SQLHelper.ROUTE_NAME + " LIKE " + "'" + searchQuery + "'" + " AND " + SQLHelper.ID_OF_SECTOR + " = " + idOfSector;

        Log.i(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Route route = new Route();

                route.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_ROUTE)));
                route.setName(c.getString(c.getColumnIndex(SQLHelper.ROUTE_NAME)));
                route.setIdOfSector(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_SECTOR)));
                route.setDifficulty(c.getString(c.getColumnIndex(SQLHelper.DIFFICULTY)));
                route.setBolts(c.getInt(c.getColumnIndex(SQLHelper.BOLTS)));
                route.setLength(c.getInt(c.getColumnIndex(SQLHelper.LENGTH)));
                route.setLatitude(c.getDouble(c.getColumnIndex(SQLHelper.LATITUTE)));
                route.setLongitude(c.getDouble(c.getColumnIndex(SQLHelper.LONGITUDE)));

                routeList.add(route);
            } while (c.moveToNext());
        }
        c.close();
        return routeList;
    }
}

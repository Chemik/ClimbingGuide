package org.climbingguide.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String LOG = SQLHelper.class.getName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "climbingGuide.db";

	public static final String TABLE_AREAS = "areas";
	public static final String ID_AREA = "id_area";
	public static final String AREA_NAME = "area_name";
	public static final String AREA_DATE = "area_date";
	
	private static final String CREATE_TABLE_AREAS = "CREATE TABLE " + TABLE_AREAS
			+ " ( " + ID_AREA + " INTEGER PRIMARY KEY AUTOINCREMENT , " 
			+ AREA_NAME + " TEXT " + " ) ;";
	private static final String DROP_TABLE_AREAS = "DROP TABLE IF EXIST " + TABLE_AREAS;
//-----------------------------------------------------------------------------------------------
	public static final String TABLE_SECTOR = "sectors";
	public static final String ID_SECTOR = "id_sector";
	public static final String SECTOR_NAME = "sector_name";
	public static final String ID_OF_AREA = "id_of_area";
	public static final String SECTOR_DATE = "sector_date";
	
	public static final String CREATE_TABLE_SECTOR = "CREATE TABLE " + TABLE_SECTOR 
			+ " ( " + ID_SECTOR + " INTEGER PRIMARY KEY AUTOINCREMENT , " 
			+ SECTOR_NAME + " TEXT , " + ID_OF_AREA + " INTEGER " + " ) ;";
	public static final String DROP_TABLE_SECTOR = "DROP TABLE IF EXIST" + TABLE_SECTOR;
	
	//---------------------------------------------------------------------------------------------
	public static final String TABLE_ROUTE = "routes";
	public static final String ID_ROUTE = "id_route";
	public static final String ROUTE_NAME = "route_name";
	public static final String ID_OF_SECTOR = "id_sector";
	public static final String DIFFICULTY = "difficulty";
	public static final String BOLTS = "bolts";
	public static final String LENGTH = "length";
	public static final String ROUTE_DATE = "route_date";
	public static final String LATITUTE = "lantitute";
	public static final String LONGITUDE = "longitude";
			
		

	public static final String CREATE_TABLE_ROUTE = "CREATE TABLE " + TABLE_ROUTE + " ( " 
			+ ID_ROUTE + " INTEGER PRIMARY KEY AUTOINCREMENT , " + ROUTE_NAME 
			+ " TEXT , " + ID_OF_SECTOR + " INTEGER , " + DIFFICULTY + " TEXT , " 
			+ BOLTS + " INTEGER , " + LENGTH + " INTEGER, " + LATITUTE + " DOUBLE, " + LONGITUDE +" DOUBLE " + " ) ;";
	private static final String DROP_TABLE_ROUTE = "DROP TABLE IF EXIST " + TABLE_ROUTE;
	
    

    public SQLHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG, "Create areas database -> " + CREATE_TABLE_AREAS);
        db.execSQL(CREATE_TABLE_AREAS);
        
        Log.i(LOG, "Create sector database -> " + CREATE_TABLE_SECTOR);
        db.execSQL(CREATE_TABLE_SECTOR);
        
        Log.i(LOG, "Create route database -> " + CREATE_TABLE_ROUTE);
        db.execSQL(CREATE_TABLE_ROUTE);
       
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_AREAS);
        db.execSQL(DROP_TABLE_SECTOR);
        db.execSQL(DROP_TABLE_ROUTE);
        onCreate(db);
    }
}

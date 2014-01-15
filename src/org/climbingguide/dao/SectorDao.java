package org.climbingguide.dao;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.model.Sector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SectorDao {

	private static final String LOG = SectorDao.class.getName();
	
	private SQLiteDatabase db;
	private SQLHelper dbHelperSector;
	
	public SectorDao(Context context){
		dbHelperSector = new SQLHelper(context);
	}
	
	public void open(){
		db = dbHelperSector.getWritableDatabase();
	}
	
	public void close(){
		dbHelperSector.close();
	}
//----------GET-ALL-SECTORS------------------------------------------------------------------------------------------------
	public List<Sector> getAllSectors()
	{	
		List<Sector> sectorList = new ArrayList<Sector>();
		
		String selectQuery = " SELECT * FROM " + SQLHelper.TABLE_SECTOR;
		Log.i(LOG,selectQuery);
		
		Cursor c = db.rawQuery(selectQuery,null);
		
		if (c.moveToFirst())
		{
			do{
				Sector sector = new Sector();
				
				
				sector.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_SECTOR)));
				sector.setName(c.getString(c.getColumnIndex(SQLHelper.SECTOR_NAME)));
				sector.setIdOfArea(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_AREA)));
						
				sectorList.add(sector);
			}while(c.moveToNext());
		}
		c.close();
		return sectorList;
	}
//-------GET-BY-ID-SECTORS------------------------------------------------------------------------------------------------
	public List<Sector> getSector(int find){
		
		List<Sector> sectorList = new ArrayList<Sector>();
		String selectQuery = " SELECT * FROM " + SQLHelper.TABLE_SECTOR + " WHERE "+SQLHelper.ID_OF_AREA + " = " + "'"+ find +"'" ;
		Log.i(LOG, selectQuery);
		
		Cursor c = db.rawQuery(selectQuery,null);
		
		if (c.moveToFirst())
		{
			do{
				Sector sector = new Sector();
				
				sector.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_SECTOR)));
				sector.setName(c.getString(c.getColumnIndex(SQLHelper.SECTOR_NAME)));
				sector.setIdOfArea(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_AREA)));
						
				sectorList.add(sector);
			}while(c.moveToNext());
		}
		c.close();
		return sectorList;
		
	}
//---------------ADD-SECTOR-----------------------------------------------------------------------------------------------
	public void addSector(Sector sector)
	{
		
		ContentValues value = new ContentValues();

		value.put(SQLHelper.SECTOR_NAME,sector.getName());
		value.put(SQLHelper.ID_OF_AREA,sector.getIdOfArea());
		
		Log.i(LOG,"Inster to table sector -->" + value);
		
		db.insert(SQLHelper.TABLE_SECTOR, null, value);
	    db.close(); 
	}
//----------------SEARCH--------------------------------------------------------------------------------------------------
	public List<Sector> sectorSearch(String query){
		
		List<Sector> sectorList = new ArrayList<Sector>();
		String searchQuery = query + "%";
		String selectQuery;
		
		selectQuery = " SELECT * FROM " + SQLHelper.TABLE_SECTOR +" WHERE " + SQLHelper.SECTOR_NAME + " LIKE " + "'" + searchQuery +"'";

		Log.i(LOG, selectQuery);
		
		Cursor c = db.rawQuery(selectQuery,null);
		
		if (c.moveToFirst())
		{
			do{
				Sector sector = new Sector();
				
				sector.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_SECTOR)));
				sector.setName(c.getString(c.getColumnIndex(SQLHelper.SECTOR_NAME)));
				sector.setIdOfArea(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_AREA)));
						
				sectorList.add(sector);
			}while(c.moveToNext());
		}
		c.close();
		return sectorList;
		
	}
//------------------------SEARCH---ID-OF-AREA---------------------------------------------------------------------------
	public List<Sector> sectorSearchId(String query,int idOfArea){
		
		List<Sector> sectorList = new ArrayList<Sector>();
		String searchQuery = query + "%";
		String selectQuery;
		
		selectQuery = " SELECT * FROM " + SQLHelper.TABLE_SECTOR +" WHERE " + SQLHelper.SECTOR_NAME + " LIKE " + "'" + searchQuery +"'" + " AND  "+SQLHelper.ID_OF_AREA + "=" +idOfArea;
		
	
		Log.i(LOG, selectQuery);
		
		Cursor c = db.rawQuery(selectQuery,null);
		
		if (c.moveToFirst())
		{
			do{
				Sector sector = new Sector();
				
				sector.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_SECTOR)));
				sector.setName(c.getString(c.getColumnIndex(SQLHelper.SECTOR_NAME)));
				sector.setIdOfArea(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_AREA)));
						
				sectorList.add(sector);
			}while(c.moveToNext());
		}
		c.close();
		return sectorList;
		
	}
	
	public Sector getSectorById(int id){
		String selectQuery = " SELECT * FROM " + SQLHelper.TABLE_SECTOR + " WHERE "+SQLHelper.ID_OF_AREA + " = " + "'"+ id +"'" ;
		Log.i(LOG, selectQuery);
		
		Cursor c = db.rawQuery(selectQuery,null);
		
		Sector sector = new Sector();
				
		sector.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_SECTOR)));
		sector.setName(c.getString(c.getColumnIndex(SQLHelper.SECTOR_NAME)));
		sector.setIdOfArea(c.getInt(c.getColumnIndex(SQLHelper.ID_OF_AREA)));
		
		
		return sector;
		
		
	}
}

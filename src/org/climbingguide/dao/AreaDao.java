package org.climbingguide.dao;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.model.Area;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AreaDao {
	
	private static final String LOG = AreaDao.class.getName();
	
	private SQLiteDatabase db;
	private SQLHelper dbHelperArea;
	
	public AreaDao(Context context){
		dbHelperArea = new SQLHelper(context);
	}
	
	public void open(){
		db = dbHelperArea.getWritableDatabase();
	}
	
	public void close(){
		dbHelperArea.close();
	}

//---------------GET-ALL-AREAS--------------------------------------------------------------------------------------------
	public List<Area> getAllAreas()
	{	
		List<Area> areaList = new ArrayList<Area>();
		
		
		String selectQuery = " SELECT * FROM " + SQLHelper.TABLE_AREAS;
		
		Log.i(LOG, selectQuery);
		
		Cursor c = db.rawQuery(selectQuery,null);
	
		if (c.moveToFirst())
		{
			do{
				Area area = new Area();
				area.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_AREA)));
				area.setName(c.getString(c.getColumnIndex(SQLHelper.AREA_NAME)));
				areaList.add(area);
				
			}while(c.moveToNext());

		}
		c.close();
		return areaList;
	}
//----------------ADD-AREAS-----------------------------------------------------------------------------------------------
	public void addArea(Area area){
		ContentValues value = new ContentValues();
		
		//value.put(SQLHelper.ID_AREA ,area.getId());
		value.put(SQLHelper.AREA_NAME,area.getName());
		
		Log.i(LOG,"Inster to table area -->" + value);
		
		db.insert(SQLHelper.TABLE_AREAS, null, value);
	    db.close();
	}
//-----------------SEARCH-------------------------------------------------------------------------------------------------
	public List<Area> areasSearch(String query)
	{	
		List<Area> areaList = new ArrayList<Area>();
		String searchQuery = query + "%";
		
		String selectQuery = " SELECT * FROM " + SQLHelper.TABLE_AREAS +" WHERE " + SQLHelper.AREA_NAME + " LIKE " + "'" + searchQuery +"'";
		
		Log.i(LOG,selectQuery);
		
		Cursor c = db.rawQuery(selectQuery,null);
	
		if (c.moveToFirst())
		{
			do{
				Area area = new Area();
				area.setId(c.getInt(c.getColumnIndex(SQLHelper.ID_AREA)));
				area.setName(c.getString(c.getColumnIndex(SQLHelper.AREA_NAME)));
				areaList.add(area);
				
			}while(c.moveToNext());

		}
		c.close();
		return areaList;
	}

}

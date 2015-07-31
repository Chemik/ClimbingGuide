package org.climbingguide.update;

import org.climbingguide.dao.SectorDao;
import org.climbingguide.model.Sector;

import android.content.Context;

public class UpdateSector {

	public void updateSector(int id, String name,int idOfArea,Context context)
	{
		Sector sector = new Sector();
		
		sector.setId(id);
		sector.setName(name);
		sector.setIdOfArea(idOfArea);
		
		SectorDao daos = new SectorDao(context);
		daos.open();
		daos.addSector(sector);
		daos.close();
	}
}

package org.climbingguide.update;

import android.content.Context;

public class Update {

	//----------------------------------------------------------
	public void updateA(Context context)
	{
		UpdateArea area = new UpdateArea();
		
		area.updateArea(1, "Drevenik",context);
		area.updateArea(2, "Turniska",context);
		area.updateArea(3, "Zamutov",context);
		area.updateArea(4, "Zadiel",context);

	
	}
//---------------------------------------------------------------	
	public void updateS(Context context)
	{
		UpdateSector sector = new UpdateSector();
		
		sector.updateSector(1,"Peklo",1,context);
		sector.updateSector(2,"Raj",1,context);
		sector.updateSector(3,"Najvyssia turna",2,context);
		sector.updateSector(4,"Velkonocna turna",2,context);
		sector.updateSector(5,"Zarastena turna",2,context);
		sector.updateSector(6,"Mur",2,context);
		sector.updateSector(7,"Klemba",2,context);
		sector.updateSector(8,"Najnisia turna",2,context);
		sector.updateSector(9,"Cervene previsy",1,context);
		sector.updateSector(10,"Marta",1,context);
		sector.updateSector(11,"Amerika",1,context);
		sector.updateSector(12,"Previsy",3,context);
				
	}
//---------------------------------------------------------------
	public void updateR(Context context)
	{
		UpdateRoute route = new UpdateRoute();
		
		route.updateRoute(1, "Moreplavec", 12, "7", 6, 10, 48.9947059,21.2347516, context);
		route.updateRoute(2, "Vecko", 12, "7-", 8, 15, 48.9947059,21.2347516, context);
		route.updateRoute(3, "Flora",3,"9/9+",6, 10, 48.9947059,21.2347516, context);
		route.updateRoute(4, "Vanicky",3, "8+",10, 17, 48.9947059,21.2347516, context);
		
	}
}

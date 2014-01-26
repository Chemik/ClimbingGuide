package org.climbingguide.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.climbingguide.dao.AreaDao;
import org.climbingguide.dao.RouteDao;
import org.climbingguide.dao.SectorDao;
import org.climbingguide.model.Area;
import org.climbingguide.model.Route;
import org.climbingguide.model.Sector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

public class Update {
	
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	
	public String readFeedFrom(String http){
	    StrictMode.setThreadPolicy(policy);
		StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(http);
	    try {
	        HttpResponse response = client.execute(httpGet);
	        StatusLine statusLine = response.getStatusLine();
	        int statusCode = statusLine.getStatusCode();
	        if (statusCode == 200) {
	          HttpEntity entity = response.getEntity();
	          InputStream content = entity.getContent();
	          BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	          String line;
	          while ((line = reader.readLine()) != null) {
	            builder.append(line);
	          }
	        } 
	      } catch (ClientProtocolException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	      return builder.toString();
		
	}

	//----------------------------------------------------------
	public void updateA(Context context)
	{
		UpdateArea area = new UpdateArea();
		AreaDao daoa = new AreaDao(context);
		daoa.open();
		List <Area> listOfAreas = daoa.getAllAreas();		
		daoa.close();
		 
		String primeAreas = readFeedFrom("http://climbingguide.madzik.sk/areas.php");
		
		try {
			JSONObject areas = new JSONObject(primeAreas);
			JSONArray arrayAreas = new JSONArray();
			arrayAreas = areas.getJSONArray("areas");
			int i =0;
			if(listOfAreas.size()<arrayAreas.length()){
				for(i=listOfAreas.size();i<arrayAreas.length();i++){
					
					JSONObject HTTPArea = new JSONObject();
					HTTPArea = arrayAreas.getJSONObject(i);
					area.updateArea(HTTPArea.getInt("id_area"), HTTPArea.getString("area_name"), context);
				}
			}
			else{
				Toast.makeText(context, "Your areas are up to date", Toast.LENGTH_LONG).show();
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
//		area.updateArea(1, "Drevenik",context);
//		area.updateArea(2, "Turniska",context);
//		area.updateArea(3, "Zamutov",context);
//		area.updateArea(4, "Zadiel",context);

	
	}
//---------------------------------------------------------------	
	public void updateS(Context context)
	{
		UpdateSector sector = new UpdateSector();
		
		SectorDao daoa = new SectorDao(context);
		daoa.open();
		List <Sector> listOfSectors = daoa.getAllSectors();		
		daoa.close();
		 
		String primeSectors = readFeedFrom("http://climbingguide.madzik.sk/sectors.php");
		
		try {
			JSONObject sectors = new JSONObject(primeSectors);
			JSONArray arraySectors = new JSONArray();
			arraySectors = sectors.getJSONArray("sectors");
			int i =0;
			if(listOfSectors.size()<arraySectors.length()){
				for(i=listOfSectors.size();i<arraySectors.length();i++){
					
					JSONObject HTTPSector = new JSONObject();
					HTTPSector = arraySectors.getJSONObject(i);
					sector.updateSector(HTTPSector.getInt("id_sector"), HTTPSector.getString("sector_name"), HTTPSector.getInt("id_of_area"), context);
				}
			}
			else{
				Toast.makeText(context, "Your sectors are up to date", Toast.LENGTH_LONG).show();
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		sector.updateSector(1,"Peklo",1,context);
//		sector.updateSector(2,"Raj",1,context);
//		sector.updateSector(3,"Najvyssia turna",2,context);
//		sector.updateSector(4,"Velkonocna turna",2,context);
//		sector.updateSector(5,"Zarastena turna",2,context);
//		sector.updateSector(6,"Mur",2,context);
//		sector.updateSector(7,"Klemba",2,context);
//		sector.updateSector(8,"Najnisia turna",2,context);
//		sector.updateSector(9,"Cervene previsy",1,context);
//		sector.updateSector(10,"Marta",1,context);
//		sector.updateSector(11,"Amerika",1,context);
//		sector.updateSector(12,"Previsy",3,context);
				
	}
//---------------------------------------------------------------
	public void updateR(Context context)
	{
		UpdateRoute route = new UpdateRoute();
		
		RouteDao daoa = new RouteDao(context);
		daoa.open();
		List <Route> listOfRoutes = daoa.getAllRoutes();		
		daoa.close();
		 
		String primeRoutes = readFeedFrom("http://climbingguide.madzik.sk/routes.php");
		
		try {
			JSONObject routes = new JSONObject(primeRoutes);
			JSONArray arrayRoutes = new JSONArray();
			arrayRoutes = routes.getJSONArray("routes");
			int i =0;
			if(listOfRoutes.size()<arrayRoutes.length()){
				for(i=listOfRoutes.size();i<arrayRoutes.length();i++){
					
					JSONObject HTTPRoute = new JSONObject();
					HTTPRoute = arrayRoutes.getJSONObject(i);
					route.updateRoute(HTTPRoute.getInt("id_route"), HTTPRoute.getString("route_name"), HTTPRoute.getInt("id_of_sector"), HTTPRoute.getString("difficulty"), HTTPRoute.getInt("bolts"), HTTPRoute.getInt("length"), 48.9947059, 21.2347516, context);
				}
			}
			else{
				Toast.makeText(context, "Your routes are up to date", Toast.LENGTH_LONG).show();
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		route.updateRoute(1, "Moreplavec", 12, "7", 6, 10, 48.9947059,21.2347516, context);
//		route.updateRoute(2, "Vecko", 12, "7-", 8, 15, 48.9947059,21.2347516, context);
//		route.updateRoute(3, "Flora",3,"9/9+",6, 10, 48.9947059,21.2347516, context);
//		route.updateRoute(4, "Vanicky",3, "8+",10, 17, 48.9947059,21.2347516, context);
		
	}
}

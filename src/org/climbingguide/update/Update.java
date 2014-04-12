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
		int f=0; 
		
		String primeAreas = readFeedFrom("http://climbingguide.madzik.sk/areas.php");
		
		try {
			JSONObject areas = new JSONObject(primeAreas);
			JSONArray arrayAreas = new JSONArray();
			arrayAreas = areas.getJSONArray("areas");
			int i =0;
			if(listOfAreas.size()<arrayAreas.length()){
				for(i=0;i<arrayAreas.length();i++){
					JSONObject HTTPArea = new JSONObject();
					HTTPArea = arrayAreas.getJSONObject(i);
					for(int j=0;j<listOfAreas.size();j++){
						if(HTTPArea.getInt("id_area")==listOfAreas.get(j).getId()){
							f=1;
						}
					}
					if(f==0){
						area.updateArea(HTTPArea.getInt("id_area"), HTTPArea.getString("area_name"), context);
					}
					else{
						f=0;
					}
				}
				Toast.makeText(context, "Your areas was updated", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(context, "Your areas are up to date", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//---------------------------------------------------------------	
	public void updateS(Context context)
	{
		UpdateSector sector = new UpdateSector();
		
		SectorDao daoa = new SectorDao(context);
		daoa.open();
		List <Sector> listOfSectors = daoa.getAllSectors();		
		daoa.close();
		int f=0;
		
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
					for(int j=0;j<listOfSectors.size();j++){
						if(HTTPSector.getInt("id_sector")==listOfSectors.get(j).getId()){
							f=1;
						}
					}
					if(f==0){
						sector.updateSector(HTTPSector.getInt("id_sector"), HTTPSector.getString("sector_name"), HTTPSector.getInt("id_of_area"), context);
					}
					else{
						f=0;
					}
				}
			}
			else{
				Toast.makeText(context, "Your sectors are up to date", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			int f=0;
			if(listOfRoutes.size()<arrayRoutes.length()){
				for(i=listOfRoutes.size();i<arrayRoutes.length();i++){
					
					JSONObject HTTPRoute = new JSONObject();
					HTTPRoute = arrayRoutes.getJSONObject(i);
					for(int j=0;j<listOfRoutes.size();j++){
						if(HTTPRoute.getInt("id_route")==listOfRoutes.get(j).getId()){
							f=1;
						}
					}
					if(f==0){
						route.updateRoute(HTTPRoute.getInt("id_route"), HTTPRoute.getString("route_name"), HTTPRoute.getInt("id_of_sector"), HTTPRoute.getString("difficulty"), HTTPRoute.getInt("bolts"), HTTPRoute.getInt("length"), 48.9947059, 21.2347516, context);
					}
					else{
						f=0;
					}
				}
			}
			else{
				Toast.makeText(context, "Your routes are up to date", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

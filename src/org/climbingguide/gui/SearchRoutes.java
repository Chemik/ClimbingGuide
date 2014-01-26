package org.climbingguide.gui;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.dao.RouteDao;
import org.climbingguide.main.R;
import org.climbingguide.model.Route;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchRoutes extends ListFragment{

	private RouteDao  getRoutes;
	private List<Route> routeList = new ArrayList<Route>();
	private Route route = new Route();
	int idOfSector;
	
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    getActivity().setTitle("Routes");
	    
	    getRoutes = new RouteDao(getActivity());  	
	    String query = getArguments().getString("query");
	    idOfSector = getArguments().getInt("idOfSector");
	    
	    getRoutes.open();
		if(idOfSector >= 0)
		{
			routeList = getRoutes.routeSearchId(query, idOfSector);
		}
		else
		{
			routeList = getRoutes.routeSearch(query);
		}
    	getRoutes.close();
    	

    	ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(getActivity(),android.R.layout.simple_list_item_1,routeList);

	    setListAdapter(adapter);
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	
		  	
		  	Bundle bundle = new Bundle();

		  	route = routeList.get(position);
		  	
		  	bundle.putString("name",route.getName());
		  	bundle.putString("difficulty",route.getDificulty());
		  	bundle.putInt("bolts",route.getBolts());
		  	bundle.putInt("length",route.getLength());
		  	
		  	bundle.putDouble("longitude",route.getLongitude());
		  	bundle.putDouble("latitude",route.getLatitute());
		  	
		  	
			FragmentRoute fragobj = new FragmentRoute();
			fragobj.setArguments(bundle);
		  
	
		  FragmentTransaction transaction = getFragmentManager().beginTransaction();
		  
		  transaction.replace(R.id.frame_container, fragobj,"Route");
		  transaction.addToBackStack(null);
		  
		  transaction.commit();		  
	  }		  
	
	  public int getIdOfSector()
	  {
		  int i;
		  if(idOfSector >= 0)
		  {
			  i = idOfSector;
		  }
		  else
		  {
			  if(routeList.isEmpty())
			  {
				  i = -1;
			  }
			  else{
				  route = routeList.get(0);
				  i = route.getIdOfSector();
			  }
		  }
		 return i;
	  }
}


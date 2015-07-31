package org.climbingguide.gui;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.dao.SectorDao;
import org.climbingguide.main.R;
import org.climbingguide.model.Sector;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentSectors extends ListFragment{
	
	private SectorDao  getSectors;
	private List<Sector> sectorList = new ArrayList<Sector>();
	private Sector sector = new Sector();
	private int idOfArea;
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    getActivity().setTitle("Sectors");
	    
	    getSectors = new SectorDao(getActivity());  	
	    idOfArea = getArguments().getInt("idOfArea");

	    getSectors.open();

	    sectorList = getSectors.getSector(idOfArea); //get by area

    	getSectors.close();
    	

    	ArrayAdapter<Sector> adapter = new ArrayAdapter<Sector>(getActivity(),android.R.layout.simple_list_item_1,sectorList);

	    setListAdapter(adapter);
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	
		  	
		  	Bundle bundle = new Bundle();

		  	sector = sectorList.get(position);
		  	
		  	bundle.putInt("idOfSector",sector.getId());
			FragmentRoutes fragobj = new FragmentRoutes();
			fragobj.setArguments(bundle);
		  
	
		  FragmentTransaction transaction = getFragmentManager().beginTransaction();
		  
		  transaction.replace(R.id.frame_container, fragobj,"Routes");
		  transaction.addToBackStack(null);
		  
		  transaction.commit();		  
	  }	
	  
	  public int getIdOfArea()
	  {
//		  int i;
//		  if(sectorList.isEmpty())
//		  {
//			  i = -1;
//		  }
//		  else{
//			  sector = sectorList.get(0);
//		  
//			  i = sector.getIdOfArea();
//		  }
		  return idOfArea;
	  }
	
}

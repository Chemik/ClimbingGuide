package org.climbingguide.gui;




import org.climbingguide.main.R;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentRoute extends Fragment {
	
		private String name;
		private String difficulty;
		private int bolts = 0;
		private int length = 0;
		
		private double latitude;
		private double longitude;
		
		
//		@Override
//		public void onActivityCreated(Bundle savedInstanceState) {
//		   super.onActivityCreated(savedInstanceState);
//		    
//	
//		  }
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
		  
		  	getActivity().setTitle("Route");
		  
			name = getArguments().getString("name");
			difficulty = getArguments().getString("difficulty");

			bolts = getArguments().getInt("bolts");
			length = getArguments().getInt("length");

			latitude = getArguments().getDouble("latitude");
			longitude = getArguments().getDouble("longitude");
			
		  View view = inflater.inflate(R.layout.route,container, false);
		  
			TextView nameView = (TextView) view.findViewById(R.id.textViewName);
			TextView difficultyView = (TextView) view.findViewById(R.id.textViewDifficulty);
			TextView boltsView = (TextView) view.findViewById(R.id.textViewBolts);
			TextView lengthView = (TextView) view.findViewById(R.id.textViewLength);
			TextView latitudeView = (TextView) view.findViewById(R.id.textViewLatitude);
			TextView longitudeView = (TextView) view.findViewById(R.id.textViewLongitude);
		  
			
			nameView.setText("Name: "+name.toString());
			difficultyView.setText("Difficulty: "+difficulty);
			
			boltsView.setText("Bolts: "+bolts);
			lengthView.setText("Length: "+length);
			
			latitudeView.setText("latitude: "+latitude);
			longitudeView.setText("longitude: "+longitude);
			
		  return view;
	  }
}

package org.climbingguide.gui;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.dao.SectorDao;
import org.climbingguide.main.R;
import org.climbingguide.model.Sector;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentCreateRoute extends Fragment{
	public int id;
	
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
		getActivity().setTitle("CreateRoute");
		View view = inflater.inflate(R.layout.create_route,container, false);
		int i = getArguments().getInt("idOfSector");
		Button b1 = (Button) view.findViewById(R.id.button1);
		
		SectorDao dao = new SectorDao(getActivity());
		dao.open();
		List<Sector> sectorList = dao.getAllSectors();
		dao.close();
		
		b1.setOnClickListener(onClickListener);
		EditText e2   = (EditText)view.findViewById(R.id.editText2);
		e2.setText(sectorList.get(i).getName());
		e2.setClickable(false);
		return view;
	}
		
		private OnClickListener onClickListener = new OnClickListener() {
		     @Override
		     public void onClick(final View v) {
				EditText e1   = (EditText)v.findViewById(R.id.editText1);
		 		
//		 		e2.setText(text);
				EditText e3   = (EditText)v.findViewById(R.id.editText3);
				EditText e4   = (EditText)v.findViewById(R.id.editText4);
				EditText e5   = (EditText)v.findViewById(R.id.editText5);
				TextView v1 = (TextView)v.findViewById(R.id.textView1);
				TextView v2 = (TextView)v.findViewById(R.id.textView2);
				TextView v3 = (TextView)v.findViewById(R.id.textView3);
				TextView v4 = (TextView)v.findViewById(R.id.textView4);
				TextView v5 = (TextView)v.findViewById(R.id.textView5);
				TextView v6 = (TextView)v.findViewById(R.id.textView6);
				
		 		JSONObject json = new JSONObject();
				
		 		try {
					json.put("route_name", e1.getText().toString());
					//json.put("sector", e2.getText().toString());
					json.put("difficulty", e3.getText().toString());
					json.put("bolts", e4.getText().toString());
					json.put("length", e5.getText().toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}

		    	try {
					v1.setText( id);
					v3.setText((CharSequence) json.get("difficulty"));
					v4.setText((CharSequence) json.get("bolts"));
					v5.setText((CharSequence) json.get("length"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		     }
		};
		
	}



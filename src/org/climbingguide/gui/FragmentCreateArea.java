package org.climbingguide.gui;


import org.climbingguide.main.R;
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

@SuppressLint("ValidFragment")
public class FragmentCreateArea extends Fragment{
	
	JSONObject json = new JSONObject();
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
		
		getActivity().setTitle("CreateArea");
		View view = inflater.inflate(R.layout.create_area,container, false);
		Button b1 = (Button) view.findViewById(R.id.button1);
		
		b1.setOnClickListener(onClickListener);
		return view;
	}

	private OnClickListener onClickListener = new OnClickListener() {
	     @Override
	     public void onClick(final View v) {
			EditText e1   = (EditText)v.findViewById(R.id.editText1);
			try {
				json.put("area_name", e1.getText().toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	     }
	};
	

}

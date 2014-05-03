package org.climbingguide.gui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.climbingguide.dao.SectorDao;
import org.climbingguide.main.R;
import org.climbingguide.model.Sector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentCreateRoute extends Fragment{
	public int id;
	EditText e1;
	EditText e2;
	EditText e3;
	EditText e4;
	EditText e5;
	TextView v1;
	TextView v2;
	TextView v3;
	TextView v4;
	TextView v5;
	TextView v6;
	int i;

	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
 
	
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
		
		getActivity().setTitle("CreateRoute");
		View view = inflater.inflate(R.layout.create_route,container, false);
		Button b1 = (Button) view.findViewById(R.id.button1);
		i = getArguments().getInt("idOfSector");

		e1 = (EditText)view.findViewById(R.id.editText1);
		e2 = (EditText)view.findViewById(R.id.editText2);
		e3   = (EditText)view.findViewById(R.id.editText3);
		e4   = (EditText)view.findViewById(R.id.editText4);
		e5   = (EditText)view.findViewById(R.id.editText5);
		v1 = (TextView)view.findViewById(R.id.textView1);
		v2 = (TextView)view.findViewById(R.id.textView2);
		v3 = (TextView)view.findViewById(R.id.textView3);
		v4 = (TextView)view.findViewById(R.id.textView4);
		v5 = (TextView)view.findViewById(R.id.textView5);
		
		SectorDao dao = new SectorDao(getActivity());
		dao.open();
		List<Sector> sectorList = dao.getAllSectors();
		dao.close();
		
		for(int j=0;j<sectorList.size();j++){
			if(sectorList.get(j).getId()==i){
				e2.setText(sectorList.get(j).getName());
			}
		}
		
		b1.setOnClickListener(onClickListener);
		EditText e2   = (EditText)view.findViewById(R.id.editText2);
		e2.setEnabled(false);
		return view;
	}
		
		private OnClickListener onClickListener = new OnClickListener() {
		     @Override
		     public void onClick(final View v) {
		    	 
		    	StrictMode.setThreadPolicy(policy);
		 		JSONObject json = new JSONObject();
		 		JSONArray array = new JSONArray();
				
		 		try {
					json.put("route_name", e1.getText());
					json.put("id_of_sector", (i));
					json.put("difficulty", e3.getText());
					json.put("bolts", e4.getText());
					json.put("length", e5.getText());
		 			
				} catch (JSONException e) {
		 			e.printStackTrace();
				}

		    	array.put(json);
		    	JSONObject json2 = new JSONObject();
		    	try {
					json2.put("routes", array);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	v6.setText(json2.toString());
		    	
		    	final String CODEPAGE = "UTF-8";
				HttpPost post = new HttpPost("http://climbingguide.madzik.sk/route.php");
				try {
					post.setEntity(new StringEntity(json2.toString(), CODEPAGE));
					Toast.makeText( null, "Zaznam bol odoslany na spracovanie", Toast.LENGTH_LONG).show();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HttpResponse resp = null;
				HttpClient httpclient = new DefaultHttpClient();
				try {
					resp = httpclient.execute(post);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				v6.setText(resp.toString());
		     }
		};
		
	}



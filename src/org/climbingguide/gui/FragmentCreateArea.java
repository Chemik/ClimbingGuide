package org.climbingguide.gui;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.climbingguide.main.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


@SuppressLint("ValidFragment")
public class FragmentCreateArea extends Fragment{
	
	JSONObject json = new JSONObject();
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	EditText e1;
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
		
		getActivity().setTitle("CreateArea");
		View view = inflater.inflate(R.layout.create_area,container, false);
		Button b1 = (Button) view.findViewById(R.id.button1);
		e1   = (EditText)view.findViewById(R.id.editText1);
		
		b1.setOnClickListener(onClickListener);
		return view;
	}

	private OnClickListener onClickListener = new OnClickListener() {
	     @Override
	     public void onClick(final View v) {
	    	StrictMode.setThreadPolicy(policy);
			try {
				json.put("area_name", e1.getText());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		JSONObject json2 = new JSONObject();
	 		JSONArray array = new JSONArray();
	 		array.put(json);
	    	try {
				json2.put("areas", array);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	final String CODEPAGE = "UTF-8";
			HttpPost post = new HttpPost("http://climbingguide.madzik.sk/area.php");
			try {
				post.setEntity(new StringEntity(json2.toString(), CODEPAGE));
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

	     }
	};
	

}

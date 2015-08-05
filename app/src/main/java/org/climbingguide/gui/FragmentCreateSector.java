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
import org.climbingguide.dao.AreaDao;
import org.climbingguide.dao.SectorDao;
import org.climbingguide.main.R;
import org.climbingguide.model.Area;
import org.climbingguide.model.Sector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class FragmentCreateSector extends Fragment {

    EditText e1;
    EditText e2;
    TextView t1;
    int i;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    JSONObject json = new JSONObject();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("CreateSector");
        View view = inflater.inflate(R.layout.create_sector, container, false);
        i = getArguments().getInt("idOfArea");
        Button b1 = (Button) view.findViewById(R.id.button1);

        AreaDao dao = new AreaDao(getActivity());
        dao.open();
        List<Area> areaList = dao.getAllAreas();
        dao.close();
        e2 = (EditText) view.findViewById(R.id.editText2);

        for (int j = 0; j < areaList.size(); j++) {
            if (areaList.get(j).getId() == i) {
                e2.setText(areaList.get(j).getName());
            }
        }

        b1.setOnClickListener(onClickListener);
        e1 = (EditText) view.findViewById(R.id.editText1);
        t1 = (TextView) view.findViewById(R.id.textView1);
        e2.setEnabled(false);
        return view;
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            StrictMode.setThreadPolicy(policy);
            JSONObject json = new JSONObject();

            try {
                json.put("sector_name", e1.getText());
                json.put("id_of_area", (i));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JSONObject json2 = new JSONObject();
            JSONArray array = new JSONArray();
            array.put(json);

            try {
                json2.put("sectors", array);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            final String CODEPAGE = "UTF-8";
            HttpPost post = new HttpPost("http://climbingguide.madzik.sk/sector.php");
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



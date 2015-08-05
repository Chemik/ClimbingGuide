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

public class FragmentCreateRoute extends Fragment {
    public int id;
    EditText eRouteName;
    EditText eInSector;
    EditText eRouteDifficulty;
    EditText eRouteLength;
    EditText eBoltsCount;
    TextView vRouteName;
    TextView vInSector;
    TextView vRouteDifficulty;
    TextView vRouteLength;
    TextView vBoltsCount;
    int m_sector_id;

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("CreateRoute");
        View view = inflater.inflate(R.layout.create_route, container, false);
        Button bSend = (Button) view.findViewById(R.id.buttonSend);
        m_sector_id = getArguments().getInt("idOfSector");

        eRouteName = (EditText) view.findViewById(R.id.editTextRouteName);
        eInSector = (EditText) view.findViewById(R.id.editTextInSector);
        eRouteDifficulty = (EditText) view.findViewById(R.id.editTextRouteDifficulty);
        eRouteLength = (EditText) view.findViewById(R.id.editTextRouteLength);
        eBoltsCount = (EditText) view.findViewById(R.id.editTextBoltsCount);
        vRouteName = (TextView) view.findViewById(R.id.textViewRouteName);
        vInSector = (TextView) view.findViewById(R.id.textViewInSector);
        vRouteDifficulty = (TextView) view.findViewById(R.id.textViewRouteDifficulty);
        vRouteLength = (TextView) view.findViewById(R.id.textViewRouteLength);
        vBoltsCount = (TextView) view.findViewById(R.id.textViewBoltsCount);

        SectorDao dao = new SectorDao(getActivity());
        dao.open();
        List<Sector> sectorList = dao.getAllSectors();
        dao.close();

        for (int j = 0; j < sectorList.size(); j++) {
            if (sectorList.get(j).getId() == m_sector_id) {
                eInSector.setText(sectorList.get(j).getName());
            }
        }

        bSend.setOnClickListener(onSendNewRoute);
        eInSector.setEnabled(false);
        return view;
    }

    private OnClickListener onSendNewRoute = new OnClickListener() {
        @Override
        public void onClick(final View v) {

            StrictMode.setThreadPolicy(policy);
            JSONObject json = new JSONObject();
            JSONArray array = new JSONArray();
            final String CODEPAGE = "UTF-8";
            HttpPost post = new HttpPost("http://climbingguide.madzik.sk/route.php");
            HttpResponse resp = null;
            HttpClient httpclient = new DefaultHttpClient();

            try {
                json.put("route_name", eRouteName.getText());
                json.put("id_of_sector", (m_sector_id));
                json.put("difficulty", eRouteDifficulty.getText());
                json.put("bolts", eRouteLength.getText());
                json.put("length", eBoltsCount.getText());

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

            try {
                post.setEntity(new StringEntity(json2.toString(), CODEPAGE));
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            try {
                resp = httpclient.execute(post);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            eRouteName.setText(null);
            eRouteDifficulty.setText(null);
            eRouteLength.setText(null);
            eBoltsCount.setText(null);
        }
    };

}



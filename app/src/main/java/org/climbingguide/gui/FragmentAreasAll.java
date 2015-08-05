package org.climbingguide.gui;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.dao.AreaDao;
import org.climbingguide.main.R;
import org.climbingguide.model.Area;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentAreasAll extends ListFragment {

    private AreaDao m_areas;
    private List<Area> m_areaList = new ArrayList<Area>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Areas");

        m_areas = new AreaDao(getActivity());
        m_areas.open();
        m_areaList = m_areas.getAllAreas();
        m_areas.close();

        ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(getActivity(), android.R.layout.simple_list_item_1, m_areaList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Area area = new Area();
        Bundle bundle = new Bundle();

        area = m_areaList.get(position);
        Log.i(null, Integer.toString(area.getId()));

        bundle.putInt("idOfArea", area.getId());
        FragmentSectors fragobj = new FragmentSectors();
        fragobj.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.frame_container, fragobj, "Sectors");
        transaction.addToBackStack(null);

        transaction.commit();
    }
}

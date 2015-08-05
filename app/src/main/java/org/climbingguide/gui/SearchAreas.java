package org.climbingguide.gui;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.dao.AreaDao;
import org.climbingguide.main.R;
import org.climbingguide.model.Area;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchAreas extends ListFragment {

    private AreaDao getAreas;
    private List<Area> areaList = new ArrayList<Area>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Areas");

        getAreas = new AreaDao(getActivity());
        String query = getArguments().getString("query");

        getAreas.open();
        areaList = getAreas.areasSearch(query);
        getAreas.close();

        ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(getActivity(), android.R.layout.simple_list_item_1, areaList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Area area = new Area();
        Bundle bundle = new Bundle();

        area = areaList.get(position);

        bundle.putInt("idOfArea", area.getId());
        FragmentSectors fragobj = new FragmentSectors();
        fragobj.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.frame_container, fragobj, "Sectors");
        transaction.addToBackStack(null);

        transaction.commit();
    }
}

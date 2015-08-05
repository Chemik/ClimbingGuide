package org.climbingguide.update;

import org.climbingguide.dao.AreaDao;
import org.climbingguide.model.Area;

import android.app.Activity;
import android.content.Context;

public class UpdateArea extends Activity {

    public void updateArea(int id, String name, Context context) {
        Area area = new Area();

        area.setId(id);
        area.setName(name);

        AreaDao daoa = new AreaDao(context);
        daoa.open();
        daoa.addArea(area);
        daoa.close();
    }
}

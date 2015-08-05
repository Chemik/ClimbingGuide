package org.climbingguide.main;

import java.util.ArrayList;
import java.util.List;

import org.climbingguide.dao.AreaDao;
import org.climbingguide.dao.RouteDao;
import org.climbingguide.dao.SectorDao;
import org.climbingguide.gui.FragmentAreasAll;
import org.climbingguide.gui.FragmentCreateArea;
import org.climbingguide.gui.FragmentCreateRoute;
import org.climbingguide.gui.FragmentCreateSector;
import org.climbingguide.gui.FragmentRoutes;
import org.climbingguide.gui.FragmentRoutesAll;
import org.climbingguide.gui.FragmentSectors;
import org.climbingguide.gui.FragmentSectorsAll;
import org.climbingguide.gui.SearchAreas;
import org.climbingguide.gui.SearchRoutes;
import org.climbingguide.gui.SearchSectors;
import org.climbingguide.model.Area;
import org.climbingguide.model.Route;
import org.climbingguide.model.Sector;
import org.climbingguide.update.Update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


public class MainActivity extends Activity implements SearchView.OnQueryTextListener {
    private static final String LOG = AreaDao.class.getName();
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //menu item
    private SearchView mSearchView;
    //for fragment taging
    private String tagFragment = "FragmentName";
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    //Dao object
    private AreaDao areaDao;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check for update
        areaDao = new AreaDao(getApplicationContext());
        areaDao.open();
        List<Area> areaList = new ArrayList<Area>();
        areaList = areaDao.getAllAreas();
        areaDao.close();
        //Update if db is empty
        if (areaList.isEmpty()) {
            Update update = new Update();
            update.updateA(getApplicationContext());
            update.updateR(getApplicationContext());
            update.updateS(getApplicationContext());
        }

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), true, getNumberOfAreas()));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1), true, getNumberOfSectors()));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, getNumberOfRoutes()));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();

        mSearchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("query", query);

        FragmentAreasAll areaAll = (FragmentAreasAll) getFragmentManager().findFragmentByTag("AreasAll");
        FragmentSectorsAll sectorAll = (FragmentSectorsAll) getFragmentManager().findFragmentByTag("SectorsAll");
        FragmentRoutesAll routeAll = (FragmentRoutesAll) getFragmentManager().findFragmentByTag("RoutesAll");

        FragmentSectors sector = (FragmentSectors) getFragmentManager().findFragmentByTag("Sectors");
        FragmentRoutes route = (FragmentRoutes) getFragmentManager().findFragmentByTag("Routes");

        if (areaAll != null && areaAll.isVisible()) {
            SearchAreas fragment = new SearchAreas();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            tagFragment = "AreasSearch";
            transaction.replace(R.id.frame_container, fragment, tagFragment);
            transaction.addToBackStack(null);
            query = "";
            transaction.commit();
        } else if (sectorAll != null && sectorAll.isVisible()) {
            SearchSectors fragment = new SearchSectors();
            fragment.setArguments(bundle);
            bundle.putInt("idOfArea", -1);
            tagFragment = "SectorsSearch";
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment, tagFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (routeAll != null && routeAll.isVisible()) {
            SearchRoutes fragment = new SearchRoutes();
            fragment.setArguments(bundle);
            bundle.putInt("idOfSector", -1);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            tagFragment = "RoutesSearch";
            transaction.replace(R.id.frame_container, fragment, tagFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (sector != null && sector.isVisible()) {
            SearchSectors fragment = new SearchSectors();
            fragment.setArguments(bundle);
            bundle.putInt("idOfArea", sector.getIdOfArea());
            tagFragment = "SectorsSearch";
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment, tagFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (route != null && route.isVisible()) {
            SearchRoutes fragment = new SearchRoutes();
            fragment.setArguments(bundle);
            bundle.putInt("idOfSector", route.getIdOfSector());
            tagFragment = "RoutesSearch";
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment, tagFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        Log.i(LOG, "TagFragmentt = " + tagFragment);
        return false;
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new FragmentAreasAll();
                tagFragment = "AreasAll";
                break;
            case 1:
                fragment = new FragmentSectorsAll();
                tagFragment = "SectorsAll";
                break;
            case 2:
                fragment = new FragmentRoutesAll();
                tagFragment = "RoutesAll";
                break;
            case 3:
                Bundle bundle = new Bundle();
                FragmentAreasAll areaAll = (FragmentAreasAll) getFragmentManager().findFragmentByTag("AreasAll");
                FragmentSectorsAll sectorAll = (FragmentSectorsAll) getFragmentManager().findFragmentByTag("SectorsAll");
                FragmentRoutesAll routeAll = (FragmentRoutesAll) getFragmentManager().findFragmentByTag("RoutesAll");

                FragmentSectors sector = (FragmentSectors) getFragmentManager().findFragmentByTag("Sectors");
                FragmentRoutes route = (FragmentRoutes) getFragmentManager().findFragmentByTag("Routes");

                if (areaAll != null && areaAll.isVisible()) {
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                        fragment = new FragmentCreateArea();
                        tagFragment = "CreateArea";
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "There is no network access", Toast.LENGTH_LONG).show();
                    }
                } else if (sectorAll != null && sectorAll.isVisible()) {
                    Toast.makeText(getApplicationContext(), "First select the area", Toast.LENGTH_LONG).show();
                    break;
                } else if (routeAll != null && routeAll.isVisible()) {
                    Toast.makeText(getApplicationContext(), "First select the sector", Toast.LENGTH_LONG).show();
                    break;
                } else if (sector != null && sector.isVisible()) {
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                        fragment = new FragmentCreateSector();
                        bundle.putInt("idOfArea", sector.getIdOfArea());
                        fragment.setArguments(bundle);
                        tagFragment = "CreateSector";
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "There is no network access", Toast.LENGTH_LONG).show();
                    }
                } else if (route != null && route.isVisible()) {
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                        fragment = new FragmentCreateRoute();
                        bundle.putInt("idOfSector", route.getIdOfSector());
                        fragment.setArguments(bundle);
                        tagFragment = "CreateRoute";
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "There is no network access", Toast.LENGTH_LONG).show();
                    }
                }
            case 4:
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    Update update = new Update();
                    update.updateA(getApplicationContext());
                    update.updateS(getApplicationContext());
                    update.updateR(getApplicationContext());
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "There is no network access", Toast.LENGTH_LONG).show();
                }
                break;
//		case 5:
//			fragment = new ();
//			break;

            default:
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment, tagFragment).addToBackStack(null).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            Log.i(LOG, "TagFragment = " + tagFragment);

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //Get number of areas , sectors,routes
    public String getNumberOfAreas() {
        int numberOfareas = 0;
        AreaDao areaDao = new AreaDao(getApplicationContext());
        List<Area> areaList = new ArrayList<Area>();

        areaDao.open();
        areaList = areaDao.getAllAreas();
        areaDao.close();

        for (Area area : areaList) {
            numberOfareas++;
        }

        return Integer.toString(numberOfareas);
    }

    public String getNumberOfSectors() {
        int numberOfsectors = 0;
        SectorDao sectorDao = new SectorDao(getApplicationContext());
        List<Sector> sectorList = new ArrayList<Sector>();

        sectorDao.open();
        sectorList = sectorDao.getAllSectors();
        sectorDao.close();

        for (Sector sector : sectorList) {
            numberOfsectors++;
        }

        return Integer.toString(numberOfsectors);
    }

    public String getNumberOfRoutes() {
        int numberOfroutes = 0;
        RouteDao routeDao = new RouteDao(getApplicationContext());
        List<Route> routeList = new ArrayList<Route>();

        routeDao.open();
        routeList = routeDao.getAllRoutes();
        routeDao.close();

        for (Route route : routeList) {
            numberOfroutes++;
        }
        return Integer.toString(numberOfroutes);
    }
}

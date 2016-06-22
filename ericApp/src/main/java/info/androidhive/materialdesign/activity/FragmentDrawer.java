package info.androidhive.materialdesign.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import info.androidhive.materialdesign.R;

public class FragmentDrawer extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RelativeLayout ChassisDetailLayout,PowerSupplyLayout,PciCardLayout;
        final RelativeLayout chassisNameMenu,powersulppyMenu,pciCardMenu;
        TextView DetailsInfoTxt,LoginstInfoTxt;

        final HomeConnectionWithFrag homeConnectionWithFrag = (HomeConnectionWithFrag)getActivity();

        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        ChassisDetailLayout = (RelativeLayout)layout.findViewById(R.id.ChassisNameLayout);
        PowerSupplyLayout = (RelativeLayout) layout.findViewById(R.id.PowerSupplyLayout);
        chassisNameMenu = (RelativeLayout) layout.findViewById(R.id.chassisNameMenu);
        powersulppyMenu = (RelativeLayout) layout.findViewById(R.id.powersulppyMenu);
        PciCardLayout = (RelativeLayout)layout.findViewById(R.id.PciCardLayout);
        pciCardMenu = (RelativeLayout)layout.findViewById(R.id.pciCardMenu);

        DetailsInfoTxt = (TextView)layout.findViewById(R.id.DetailsInfoTxt);
        LoginstInfoTxt = (TextView)layout.findViewById(R.id.LoginstInfoTxt);

        ChassisDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chassisNameMenu.getVisibility() == View.GONE)
                    chassisNameMenu.setVisibility(View.VISIBLE);
                else
                    chassisNameMenu.setVisibility(View.GONE);
            }
        });

        PowerSupplyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(powersulppyMenu.getVisibility() == View.GONE)
                    powersulppyMenu.setVisibility(View.VISIBLE);
                else
                    powersulppyMenu.setVisibility(View.GONE);
            }
        });

        PciCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pciCardMenu.getVisibility() == View.GONE)
                    pciCardMenu.setVisibility(View.VISIBLE);
                else
                    pciCardMenu.setVisibility(View.GONE);
            }
        });

        DetailsInfoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeConnectionWithFrag.triggerToCloseDrawer();
                homeConnectionWithFrag.addFragmentDeatilsInfo();
            }
        });

        LoginstInfoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeConnectionWithFrag.triggerToCloseDrawer();
                homeConnectionWithFrag.addFragmentLogistInfo();
            }
        });

        return layout;
    }

    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar) {
        //containerView = getActivity().findViewById(fragmentId);
        //DrawerLayout mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
}

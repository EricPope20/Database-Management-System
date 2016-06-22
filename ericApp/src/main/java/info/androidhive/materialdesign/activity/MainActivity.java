package info.androidhive.materialdesign.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;

public class MainActivity extends AppCompatActivity implements HomeConnectionWithFrag{

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private FragmentManager fManager;

    private DrawerLayout mDrawerLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private MenuItem playMenu;

    private ArrayList<String> serverNames;
    private ArrayAdapter<String> adapter;
    private String selectedServer = "";


    @Override
    public void onBackPressed() {
        if(fManager.getBackStackEntryCount() > 1) {
            fManager.popBackStack();
        }else if(fManager.getBackStackEntryCount() == 1){
            super.onBackPressed();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        //getting the toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.myEditText);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp( (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        //drawerFragment.setDrawerListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fManager = getFragmentManager();

        serverNames = new ArrayList<>();
        serverNames.add("Hps100");
        serverNames.add("Hps200");
        serverNames.add("Hps300");
        serverNames.add("Hps400");
        serverNames.add("Hps500");



        autoCompleteTextView.requestFocus();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, serverNames);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);

        // display the first navigation drawer view on app launch
        displayView(0,"");

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String detselectedServer = autoCompleteTextView.getText().toString();

                selectedServer = detselectedServer + 'l';
                detselectedServer += 'd';
                displayView(3,detselectedServer);

                //Toast.makeText(getApplicationContext(),selectedServer,Toast.LENGTH_SHORT).show();
            }
        });

        createDatabaseAndTables();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        playMenu = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item if needed
        int id = item.getItemId();

        if(id == R.id.action_search){
            if(autoCompleteTextView.getVisibility() == View.VISIBLE){
                autoCompleteTextView.setVisibility(View.GONE);
                playMenu.setIcon(R.drawable.ic_action_search);
            }else {
                autoCompleteTextView.setVisibility(View.VISIBLE);
                playMenu.setIcon(R.drawable.ic_keyboard_arrow_down_white_24dp);
            }
            //Toast.makeText(getApplicationContext(),"searchhh", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    //displaying the items
    private void displayView(int position,String serverName) {
        String title;

        //using switch statement to loop through
        switch (position) {
            case 0:
                HomeFragment fragment = new HomeFragment();
                title = getString(R.string.title_home);

                FragmentTransaction fragmentTransaction = fManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                // set the toolbar title
                getSupportActionBar().setTitle(title);

                break;
            case 1:
                HostInfoFragment fragment2 = new HostInfoFragment();
                title = getString(R.string.title_host_info);

                FragmentTransaction fragmentTransaction2 = fManager.beginTransaction();
                fragmentTransaction2.replace(R.id.container_body, fragment2);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();
                // set the toolbar title
                getSupportActionBar().setTitle(title);

                break;

            case 2:
                LogistInfoFragment fragment3 = new LogistInfoFragment();
                title = getString(R.string.title_logist_info);

                FragmentTransaction fragmentTransaction3 = fManager.beginTransaction();
                fragmentTransaction3.replace(R.id.container_body, fragment3);
                fragmentTransaction3.addToBackStack(null);
                fragmentTransaction3.commit();

                if(selectedServer.length() != 0)
                    fragment3.getServerName(selectedServer);
                else
                    fragment3.getServerName(serverName);
                // set the toolbar title
                getSupportActionBar().setTitle(title);

                break;

            case 3:
                DeatilsInfoFragment fragment4 = new DeatilsInfoFragment();
                title = getString(R.string.title_details_info);

                FragmentTransaction fragmentTransaction4 = fManager.beginTransaction();
                fragmentTransaction4.replace(R.id.container_body, fragment4);
                fragmentTransaction4.addToBackStack(null);
                fragmentTransaction4.commit();

                fragment4.getServerName(serverName);
                // set the toolbar title
                getSupportActionBar().setTitle(title);

                break;
            default:
                break;
        }

    }

    @Override
    public void triggerOnCreateOptionMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void triggerToCloseDrawer() {
        //getActionBar().hide();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void addFragmentHostInfo() {
        displayView(1,"");
    }

    @Override
    public void addFragmentLogistInfo() {
        displayView(2,"Hps100l");
    }

    @Override
    public void addFragmentDeatilsInfo() {
        displayView(3,"Hps100d");
    }

    @Override
    public void setExactTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    //Database Mehtod
    private void createDatabaseAndTables(){

        //create details info table 1
        try {
            Sqlitehelper ob = new Sqlitehelper(this);
            ob.open();


            if(ob.CheckTableExists("Hps100d")){
                //Toast.makeText(getApplicationContext(),"Table exists",Toast.LENGTH_SHORT).show();
                //ob.dropTable("Hps100d");
                //ob.dropTable("Hps100l");
            }else {
               // Toast.makeText(getApplicationContext(),"Table not exists",Toast.LENGTH_SHORT).show();

                // create table and data store for details info Page
                ob.CreateTable("Hps100d");
                ob.storeData("Hps100d","Display Name :	Hps100");
                ob.storeData("Hps100d","Ref Template :  HP dl380p gen8_Spec C 10GB");
                ob.storeData("Hps100d","Type :  Chassis");
                ob.storeData("Hps100d","Type State :  Physical");
                ob.storeData("Hps100d","Serial Number:  2M242s100");
                ob.storeData("Hps100d","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps100d","Status :  Present");
                ob.storeData("Hps100d","Power Status :  On");
                ob.storeData("Hps100d","Created On :  8.1.2015 2:00:01 Pm");
                ob.storeData("Hps100d","Created By :  John Adam");
                ob.storeData("Hps100d","Updated On :  8.20.2015 4:10:15 Pm");
                ob.storeData("Hps100d","Updated By :  Eric Smith");

                //create table and store data for logist info page
                ob.CreateTable("Hps100l");
                ob.storeData("Hps100l","Hardware Name :  Hps100");
                ob.storeData("Hps100l","Serial Number :  2M242s100");
                ob.storeData("Hps100l","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps100l","Status :  Present");
                ob.storeData("Hps100l","Power Status :  On");
                ob.storeData("Hps100l","Budget Owner :  ");
                ob.storeData("Hps100l","Servicer :  Hewlett Packard");
                ob.storeData("Hps100l","Audit Date :  8.20.2015 4:10:15 Pm");

                //server 2

                // create table and data store for details info Page
                ob.CreateTable("Hps200d");
                ob.storeData("Hps200d","Display Name :	Hps200");
                ob.storeData("Hps200d","Ref Template :  HP dl380p gen8_Spec D 10GB");
                ob.storeData("Hps200d","Type :  Chassis");
                ob.storeData("Hps200d","Type State :  Physical");
                ob.storeData("Hps200d","Serial Number:  2M242s200");
                ob.storeData("Hps200d","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps200d","Status :  Present");
                ob.storeData("Hps200d","Power Status :  On");
                ob.storeData("Hps200d","Created On :  9.1.2015 2:00:01 Pm");
                ob.storeData("Hps200d","Created By :  John Adam");
                ob.storeData("Hps200d","Updated On :  8.20.2015 4:10:15 Pm");
                ob.storeData("Hps200d","Updated By :  Eric Pope");

                //create table and store data for logist info page
                ob.CreateTable("Hps200l");
                ob.storeData("Hps200l","Hardware Name :  Hps200");
                ob.storeData("Hps200l","Serial Number :  2M242s200");
                ob.storeData("Hps200l","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps200l","Status :  Present");
                ob.storeData("Hps200l","Power Status :  On");
                ob.storeData("Hps200l","Budget Owner :  ");
                ob.storeData("Hps200l","Servicer :  Hewlett Packard");
                ob.storeData("Hps200l","Audit Date :  9.20.2015 4:10:15 Pm");

                //server 3

                // create table and data store for details info Page
                ob.CreateTable("Hps300d");
                ob.storeData("Hps300d","Display Name :	Hps300");
                ob.storeData("Hps300d","Ref Template :  HP dl380p gen8_Spec F 10GB");
                ob.storeData("Hps300d","Type :  Chassis");
                ob.storeData("Hps300d","Type State :  Physical");
                ob.storeData("Hps300d","Serial Number:  2M242s300");
                ob.storeData("Hps300d","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps300d","Status :  Present");
                ob.storeData("Hps300d","Power Status :  On");
                ob.storeData("Hps300d","Created On :  8.1.2015 2:00:01 Pm");
                ob.storeData("Hps300d","Created By :  John Paul");
                ob.storeData("Hps300d","Updated On :  8.20.2015 4:10:15 Pm");
                ob.storeData("Hps300d","Updated By :  Eric Oscar");

                //create table and store data for logist info page
                ob.CreateTable("Hps300l");
                ob.storeData("Hps300l","Hardware Name :  Hps300");
                ob.storeData("Hps300l","Serial Number :  2M242s300");
                ob.storeData("Hps300l","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps300l","Status :  Present");
                ob.storeData("Hps300l","Power Status :  On");
                ob.storeData("Hps300l","Budget Owner :  ");
                ob.storeData("Hps300l","Servicer :  Hewlett Packard");
                ob.storeData("Hps300l","Audit Date :  4.20.2015 4:10:15 Pm");

                //server 4

                // create table and data store for details info Page
                ob.CreateTable("Hps400d");
                ob.storeData("Hps400d","Display Name :	Hps400");
                ob.storeData("Hps400d","Ref Template :  HP dl380p gen8_Spec S 10GB");
                ob.storeData("Hps400d","Type :  Chassis");
                ob.storeData("Hps400d","Type State :  Physical");
                ob.storeData("Hps400d","Serial Number:  2M242s400");
                ob.storeData("Hps400d","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps400d","Status :  Present");
                ob.storeData("Hps400d","Power Status :  On");
                ob.storeData("Hps400d","Created On :  10.1.2015 2:00:01 Pm");
                ob.storeData("Hps400d","Created By :  John Adam");
                ob.storeData("Hps400d","Updated On :  8.20.2015 4:10:15 Pm");
                ob.storeData("Hps400d","Updated By :  Paul Smith");

                //create table and store data for logist info page
                ob.CreateTable("Hps400l");
                ob.storeData("Hps400l","Hardware Name :  Hps400");
                ob.storeData("Hps400l","Serial Number :  2M242s400");
                ob.storeData("Hps400l","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps400l","Status :  Present");
                ob.storeData("Hps400l","Power Status :  On");
                ob.storeData("Hps400l","Budget Owner :  ");
                ob.storeData("Hps400l","Servicer :  Hewlett Packard");
                ob.storeData("Hps400l","Audit Date :  8.20.2015 4:10:15 Pm");

                //server 5

                // create table and data store for details info Page
                ob.CreateTable("Hps500d");
                ob.storeData("Hps500d","Display Name :	Hps500");
                ob.storeData("Hps500d","Ref Template :  HP dl380p gen8_Spec Y 10GB");
                ob.storeData("Hps500d","Type :  Chassis");
                ob.storeData("Hps500d","Type State :  Physical");
                ob.storeData("Hps500d","Serial Number:  2M242s500");
                ob.storeData("Hps500d","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps500d","Status :  Present");
                ob.storeData("Hps500d","Power Status :  On");
                ob.storeData("Hps500d","Created On :  8.30.2015 4:00:01 Pm");
                ob.storeData("Hps500d","Created By :  John Adam");
                ob.storeData("Hps500d","Updated On :  8.20.2015 4:10:15 Pm");
                ob.storeData("Hps500d","Updated By :  Eric Doudo");

                //create table and store data for logist info page
                ob.CreateTable("Hps500l");
                ob.storeData("Hps500l","Hardware Name :  Hps500");
                ob.storeData("Hps500l","Serial Number :  2M242s500");
                ob.storeData("Hps500l","Manufacturer :  Hewlett Packard");
                ob.storeData("Hps500l","Status :  Present");
                ob.storeData("Hps500l","Power Status :  On");
                ob.storeData("Hps500l","Budget Owner :  ");
                ob.storeData("Hps500l","Servicer :  Hewlett Packard");
                ob.storeData("Hps500l","Audit Date :  10.20.2015 4:10:15 Pm");
            }


            ob.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        // create Host info table 1


        //create logist info table 1
    }
}
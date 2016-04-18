package jwg.eliteinventory.mainactivities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.android.gms.vision.barcode.Barcode;
import jwg.eliteinventory.R;
import jwg.eliteinventory.barcodeactivities.BarcodeCaptureActivity;
import jwg.eliteinventory.navigationdrawer.DrawerAdapter;
import jwg.eliteinventory.settingsactivities.MyPrefsActivity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.api.CommonStatusCodes;
import java.util.ArrayList;

/** Created by John **/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /** Barcode **/
    private static final int RC_BARCODE_CAPTURE = 9001; //onActivityResult Code
    private static final String TAG = "BarcodeMain";

    /** Navigation Drawer **/
    //First We Declare Titles And Icons For Our Navigation Drawer List View
    //This Icons And Titles Are held in an Array as you can see

    String TITLES[] = {"About","Home","E-Mail","Settings","Clear List"};
    int ICONS[] = {R.drawable.ic_about_page,
            R.drawable.ic_action_home,
            R.drawable.ic_action_gmail,
            R.drawable.ic_action_settings,
            R.drawable.ic_action_delete};

    String NAME = "John Graham";
    String EMAIL = "jgraham@athoscomputing.com";
    int PROFILE = R.mipmap.app_icon;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;

    /** Custom List View Adapter **/
    CustomListAdapter adapter;
    ArrayList<String> barCodes;
    ArrayList<ListItems> scannedItems;
    boolean displayFakeData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Sets up the ArrayLists for the Scanned items list view **/
        barCodes = new ArrayList<>();
        scannedItems = new ArrayList<>();
        adapter = new CustomListAdapter(this, scannedItems);


        /** Ability to tap and delete an entry **/
        adapter = new CustomListAdapter(this, scannedItems);
        ListView listView = (ListView) findViewById(R.id.custom_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete this entry?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        barCodes.remove(positionToRemove);
                        adapter.remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });

        /** Begin Fake Data Set on launch **/
        if(displayFakeData){
            ListItems newEntry = new ListItems("Welcome!", R.mipmap.green_check_mark);
            ListItems newEntry2 = new ListItems("Press the Camera Button...", R.mipmap.green_check_mark);
            ListItems newEntry3 = new ListItems("To start scanning Barcodes!", R.mipmap.green_check_mark);
            ListItems newEntry4 = new ListItems("Once Scanning Starts...", R.mipmap.green_check_mark);
            ListItems newEntry5 = new ListItems("This Data will be cleared!", R.mipmap.green_check_mark);
            barCodes.add("Welcome!");
            barCodes.add("Press the Camera Button...");
            barCodes.add("To start scanning Barcodes!");
            barCodes.add("Once Scanning Starts...");
            barCodes.add("This Data will be cleared!");
            adapter.add(newEntry);
            adapter.add(newEntry2);
            adapter.add(newEntry3);
            adapter.add(newEntry4);
            adapter.add(newEntry5);
            displayFakeData = false;
        }
        /** End Fake Data Set **/

        /** Initializes Material Design Bar at top of screen **/
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        /** Begin Navigation Drawer **/
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DrawerAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE, this);
        mRecyclerView.setAdapter(mAdapter);  // Setting the adapter to Recy

        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    Drawer.closeDrawers();
                    onTouchDrawer(recyclerView.getChildPosition(child));

                    return true;
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer, toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /** Code goes here if I wanted something to happen when drawer opens **/
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                /** Code goes here if I wanted something to happen when drawer closes **/
            }
        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        /** End Navigation Drawer **/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_send_email:
                emailIntent();
                break;
            case R.id.action_delete:
                clearList();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    public void onTouchDrawer(final int position){
        if (position == 0){
            Drawer.closeDrawers();
        }
        else if(position == 1){
            Intent intent = new Intent(this, About_Page.class);
            startActivity(intent);
        }
        else if(position == 2){
            Drawer.closeDrawers();
        }
        else if(position == 3){
            emailIntent();
        }
        else if(position == 4){
            Intent intent = new Intent();
            intent.setClass(this, MyPrefsActivity.class);
            startActivityForResult(intent, 1002);
        }
        else if(position == 5){
            clearList();
        }
    }

    private void emailIntent(){

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String finalEmailBarcode = "";

        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inventory List");
        int i=1;
        for(String item: barCodes){
            finalEmailBarcode = finalEmailBarcode + String.valueOf(i) +
                    ". " + item + "\n";
            i++;
        }
        emailIntent.putExtra(Intent.EXTRA_TEXT, finalEmailBarcode);

        try {
            startActivity(emailIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email applications installed.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto_Focus = myPrefs.getBoolean("auto_focus_key", false);
        boolean use_Flash = myPrefs.getBoolean("use_flash_key", false);
        PackageManager pm = getPackageManager();

        if(use_Flash){
            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                // this device has a camera
                use_Flash = true;
            }
            else{
                // no camera on this device
                use_Flash = false;
                myPrefs.edit().putBoolean("use_flash_key", false).apply();
                Snackbar.make(v, "No Flash on this device!", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();

            }
        }

        if (v.getId() == R.id.fab_camera) {
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);

            if(auto_Focus){
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                Log.d(TAG, "Auto Focus is: " + auto_Focus);
            }
            if(!auto_Focus){
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, false);
                Log.d(TAG, "Auto Focus is: " + auto_Focus);
            }
            if(use_Flash){
                intent.putExtra(BarcodeCaptureActivity.UseFlash, true);
                Log.d(TAG, "Use Flash is: " + use_Flash);
            }
            if(!use_Flash){
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
                Log.d(TAG, "Use Flash is: " + use_Flash);
            }

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

    }

    private void clearList(){
        adapter.clear();
        barCodes.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!displayFakeData) {
            adapter.clear();
            barCodes.clear();
            displayFakeData = true;
        }

        if (requestCode == RC_BARCODE_CAPTURE) {

            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    /** Adds Barcode the ListView **/
                    String barcodeResult = barcode.displayValue;

                    ListItems newEntry = new ListItems(barcodeResult, R.mipmap.green_check_mark);
                    barCodes.add(barcodeResult);
                    adapter.add(newEntry);
                    adapter.notifyDataSetChanged();

                    /** Alerts user it was successful **/
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.barcode_success, Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.GREEN);
                    snackbar.show();

                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.barcode_failure, Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();

                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), R.string.barcode_error, Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .setAction("Action", null).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
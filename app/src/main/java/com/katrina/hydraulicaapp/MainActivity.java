package com.katrina.hydraulicaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Hydraulica";
    private static final int MY_PERMISSION_REQUEST_ADD_CONTACT = 1;
    public android.app.Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Katrina Copeland");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is the Hydraulica App", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.calculate_power  ) {
           //displays the fragment for the cylinder calculation
            fragment = new CalculatePowerFragment();

        } else if (id == R.id.add_supplier) {
            //checks permissions and if granted, allows you to add
            makeNewContact();

        } else if (id == R.id.contact_us) {
//displays the fragment for the contact page
            fragment = new ContactUsFragment();

        } else if (id == R.id.about) {
            fragment = new AboutFragment();

        } else if (id == R.id.exit) {
            finish();
        }
        else if (id == R.id.map){
            //explicit intent
            Intent i = new Intent(this, MapsActivity.class);
            i.putExtra("lat", 43.7263598);
            i.putExtra("long", -79.231176);
            Log.d(TAG, "Activity Manager Started");
            startActivity(i);
            Log.d(TAG, "Activity Manager Started");
        }
        changeFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //=================== CUSTOM METHODS ====================

    public void changeFragment(android.app.Fragment fragment){
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }


    public void makeNewContact(){
        //Determines whether you have been granted a particular permission, returns 0 if granted, returns -1 if not
        //takes in as arguments context and name of permission to check
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);

       // if (permissionCheck != PackageManager.PERMISSION_GRANTED){//if the permission is not granted...
            //in the case where the package manager has not been granted the permission to CALL PHONE, in that case request permission from the user at runtime
            //create a static activity on the fly/dynamically - temp view/box that will say accept or deny
            //in this case array only asking for one permission , however can make multiple requests if needed using the array
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSION_REQUEST_ADD_CONTACT);
            //arguments: target activity, the requested permission, request code (Application specific request code to match with a result reported to ), will be used later in switch statement
       // }else{
            addContact();

      //  }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[]grantResults){
        switch (requestCode){
            //switch statement  to handlde multiple permission requests
            case MY_PERMISSION_REQUEST_ADD_CONTACT:{
                //grant results will be an array of 1 and -1's confirming if permissions were granted for the request(s)
                //if the lengths of the results array has at least one member, and if the result at position 1 is equal to 1, meaning permsision was granted
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    addContact();
                }
            }
        }
    }


    //step 1
    private void addContact(){

        // Creates a new Intent to insert a contact
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        // Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            startActivity(intent);
        }
    }

}

package com.example.pprzy.eZdrowie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.pprzy.eZdrowie.sqlite.MySQLiteHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView txtProfileName;

    //----------------------------------------------------------

    public Button button_next;
    boolean isExisting;
    int layoutId=0;

    public void init() {
        button_next = (Button) findViewById(R.id.button_confirm_notification1);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToNextView = new Intent(MainActivity.this, BasicInputActivity.class);
                startActivity(GoToNextView);
            }
        });
    }


    //----------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MySQLiteHelper db = new MySQLiteHelper(this);
        isExisting = db.isExistingBasic();

        //checking is there any existing records in basic data table to chose correct layout
        if (isExisting == false){
            layoutId = R.layout.activity_main2;
        }
        else {
            layoutId = R.layout.activity_main;
        }

        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (layoutId == R.layout.activity_main){
            init();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set username on header
        String userName = db.getNameBasic();
        txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewNameHeader);
        txtProfileName.setText(userName);
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

        if (id == R.id.nav_home) {
            //--------------------
            // ----------------------
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_weight) {

            //------------------------------------------
                Intent i = new Intent(MainActivity.this, WeightActivity.class);
                startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_hpressure) {

            //------------------------------------------
            Intent i = new Intent(MainActivity.this, PulseActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_sleep) {

            // ----------------------
            Intent i = new Intent(MainActivity.this, SleepActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_daily_activity) {

            // ----------------------
            Intent i = new Intent(MainActivity.this, PhysActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notes) {

            // ----------------------
            Intent i = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notifications) {

            // ----------------------
            Intent i = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_settings) {
            // ----------------------
            Intent i = new Intent(MainActivity.this, BasicInputActivity.class);
            startActivity(i);
            //------------------------------------------
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

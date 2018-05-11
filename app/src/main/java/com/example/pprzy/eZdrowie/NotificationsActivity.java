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
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pprzy.eZdrowie.sqlite.MySQLiteHelper;

import java.util.Calendar;


public class NotificationsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        TimePicker TimePickerForGettingTime;
        TimePicker TimePickerForGettingTime2;
        TextView txtProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MySQLiteHelper db = new MySQLiteHelper(this);

        //set 24h time format
        TimePickerForGettingTime = (TimePicker)findViewById(R.id.timePicker1);
        TimePickerForGettingTime.setIs24HourView(true);

        TimePickerForGettingTime2 = (TimePicker)findViewById(R.id.timePicker2);
        TimePickerForGettingTime2.setIs24HourView(true);


        //set first (morning) notification
        findViewById(R.id.button_confirm_notification1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,TimePickerForGettingTime.getCurrentHour());
                calendar.set(Calendar.MINUTE,TimePickerForGettingTime.getCurrentMinute());

                Intent intent = new Intent(getApplicationContext(),NotificationReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                Toast.makeText(NotificationsActivity.this,
                        "Ustawiono przypomnienie", Toast.LENGTH_SHORT).show();
            }
        });
        //----------

        //----------
        findViewById(R.id.button_confirm_notification2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar2 = Calendar.getInstance();

                calendar2.set(Calendar.HOUR_OF_DAY,TimePickerForGettingTime2.getCurrentHour());
                calendar2.set(Calendar.MINUTE,TimePickerForGettingTime2.getCurrentMinute());

                Intent intent2 = new Intent(getApplicationContext(),NotificationReciever.class);

                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(),101,intent2,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent2);

                Toast.makeText(NotificationsActivity.this,
                        "Ustawiono przypomnienie", Toast.LENGTH_SHORT).show();
            }
        });
        //----------

        //----------
        findViewById(R.id.button_cancel_notification1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),NotificationReciever.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(pendingIntent);

                Toast.makeText(NotificationsActivity.this,
                        "Usunięto alarm", Toast.LENGTH_SHORT).show();
            }
        });
        //----------

        //----------
        findViewById(R.id.button_cancel_notification2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(),NotificationReciever.class);

                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(),101,intent2,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager2.cancel(pendingIntent2);

                Toast.makeText(NotificationsActivity.this,
                        "Usunięto alarm", Toast.LENGTH_SHORT).show();
            }
        });
        //----------


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



//navview username
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
        getMenuInflater().inflate(R.menu.notifications, menu);
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
            Intent i = new Intent(NotificationsActivity.this, MainActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_weight) {

            //------------------------------------------
            Intent i = new Intent(NotificationsActivity.this, WeightActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_hpressure) {

            Intent i = new Intent(NotificationsActivity.this, PulseActivity.class);
            startActivity(i);
            //---

        } else if (id == R.id.nav_sleep) {

            // ----------------------
            Intent i = new Intent(NotificationsActivity.this, SleepActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_daily_activity) {

            // ----------------------
            Intent i = new Intent(NotificationsActivity.this, PhysActivity.class);
            startActivity(i);
            //------------------------------------------
        } else if (id == R.id.nav_notes) {

            // ----------------------
            Intent i = new Intent(NotificationsActivity.this, NotesActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_settings) {
            // ----------------------
            Intent i = new Intent(NotificationsActivity.this, BasicInputActivity.class);
            startActivity(i);
            //------------------------------------------
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

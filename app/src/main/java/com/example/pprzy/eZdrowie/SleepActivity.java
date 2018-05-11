package com.example.pprzy.eZdrowie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pprzy.eZdrowie.model.Sleep;
import com.example.pprzy.eZdrowie.sqlite.MySQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btn_zatwierdz, btn_pokaz;
    String typed_scale, typed_TTGS, typed_TOS, typed_NOA;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = sdf.format(c.getTime());

    SimpleDateFormat sdfFromDatePicker = new SimpleDateFormat( "yyyy-MM-dd");
    String formattedDateFromDatePicker;
    int year;
    int month;
    int day;
    DatePicker datePicker;
    boolean sleep_exist;
    TextView txtProfileName;

    RadioButton rbS1, rbS2, rbS3, rbS4, rbS5, rbTTGS1, rbTTGS2, rbTTGS3, rbTOS1, rbTOS2, rbTOS3, rbNOA1, rbNOA2, rbNOA3, rbNOA4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //add to data baseH

        final MySQLiteHelper db = new MySQLiteHelper(this);

        btn_zatwierdz = (Button) findViewById(R.id.button_confirm_notification1);
        rbS1 = (RadioButton) findViewById(R.id.ocena1);
        rbS2 = (RadioButton) findViewById(R.id.ocena2);
        rbS3 = (RadioButton) findViewById(R.id.ocena3);
        rbS4 = (RadioButton) findViewById(R.id.ocena4);
        rbS5 = (RadioButton) findViewById(R.id.ocena5);

        rbTTGS1 = (RadioButton) findViewById(R.id.time1);
        rbTTGS2 = (RadioButton) findViewById(R.id.time2);
        rbTTGS3 = (RadioButton) findViewById(R.id.time3);

        rbTOS1 = (RadioButton) findViewById(R.id.time_1);
        rbTOS2 = (RadioButton) findViewById(R.id.time_2);
        rbTOS3 = (RadioButton) findViewById(R.id.time_3);

        rbNOA1 = (RadioButton) findViewById(R.id.none_break);
        rbNOA2 = (RadioButton) findViewById(R.id.break1);
        rbNOA3 = (RadioButton) findViewById(R.id.break2);
        rbNOA4 = (RadioButton) findViewById(R.id.break3);





        btn_zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //walka z radiobuttons

                if(rbS1.isChecked()==true) {
                    typed_scale = "1";
                }
                if(rbS2.isChecked()==true) {
                    typed_scale = "2";
                }
                if(rbS3.isChecked()==true) {
                    typed_scale = "3";
                }
                if(rbS4.isChecked()==true) {
                    typed_scale = "4";
                }
                if(rbS5.isChecked()==true) {
                    typed_scale = "5";
                }

                //TTGS

                if(rbTTGS1.isChecked()==true) {
                    typed_TTGS = "<20";
                }
                if(rbTTGS2.isChecked()==true) {
                    typed_TTGS = "20-40";
                }
                if(rbTTGS3.isChecked()==true) {
                    typed_TTGS = ">40";
                }

                //TOS
                if(rbTOS1.isChecked()==true) {
                    typed_TOS = "<7h";
                }
                if(rbTOS2.isChecked()==true) {
                    typed_TOS = "7-9h";
                }
                if(rbTOS3.isChecked()==true) {
                    typed_TOS = ">9h";
                }

                //NOA
                if(rbNOA1.isChecked()==true) {
                    typed_NOA = "0";
                }
                if(rbNOA2.isChecked()==true) {
                    typed_NOA = "1-2";
                }
                if(rbNOA3.isChecked()==true) {
                    typed_NOA = "2-4";
                }
                if(rbNOA4.isChecked()==true) {
                    typed_NOA = ">4";
                }

                //------
                sleep_exist = db.isExistingSleep(formattedDate);
                if (sleep_exist == false) {

                    db.addSleep(new Sleep(formattedDate,typed_scale, typed_TTGS,typed_TOS,typed_NOA));
                    Toast.makeText(SleepActivity.this,
                            "Pomyślnie zapisano!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SleepActivity.this,
                            "Dzisiejsze dane snu zostały już wprowadzone.", Toast.LENGTH_LONG).show();
                }}
        });


//show by date

        btn_pokaz = (Button) findViewById(R.id.button_show_sleep);



        btn_pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----
                datePicker = (DatePicker) findViewById(R.id.datePickerSleep);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());


                String str_scale = db.getSCByDate(formattedDateFromDatePicker);
                TextView tv_scale_fromdate = (TextView) findViewById(R.id.textViewSleepScaleFromDate);
                tv_scale_fromdate.setText(tv_scale_fromdate.getText()+str_scale);

                String str_TTGS = db.getTTGSByDate(formattedDateFromDatePicker);
                TextView tv_ttgs_fromdate = (TextView) findViewById(R.id.textViewSleepTime1FromDate);
                tv_ttgs_fromdate.setText(tv_ttgs_fromdate.getText()+str_TTGS);

                String str_TOS = db.getTOSByDate(formattedDateFromDatePicker);
                TextView tv_tos_fromdate = (TextView) findViewById(R.id.textViewSleepTime2FromDate);
                tv_tos_fromdate.setText(tv_tos_fromdate.getText()+str_TOS);

                String str_NOA = db.getNOAByDate(formattedDateFromDatePicker);
                TextView tv_noa_fromdate = (TextView) findViewById(R.id.textViewSleepBreaksFromDate);
                tv_noa_fromdate.setText(tv_noa_fromdate.getText()+str_NOA);

                Toast.makeText(SleepActivity.this,
                        "Wyświetlono dane", Toast.LENGTH_SHORT).show();
            }
        });



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
        getMenuInflater().inflate(R.menu.sleep, menu);
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
            Intent i = new Intent(SleepActivity.this, MainActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_weight) {

            //------------------------------------------
            Intent i = new Intent(SleepActivity.this, WeightActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_hpressure) {

            //------------------------------------------
            Intent i = new Intent(SleepActivity.this, PulseActivity.class);
            startActivity(i);
            //------------------------------------------


        } else if (id == R.id.nav_sleep) {


        } else if (id == R.id.nav_daily_activity) {

            // ----------------------
            Intent i = new Intent(SleepActivity.this, PhysActivity.class);
            startActivity(i);
            //------------------------------------------

        }else if (id == R.id.nav_notes) {

            // ----------------------
            Intent i = new Intent(SleepActivity.this, NotesActivity.class);
            startActivity(i);
            //------------------------------------------

        }else if (id == R.id.nav_notifications) {

            // ----------------------
            Intent i = new Intent(SleepActivity.this, NotesActivity.class);
            startActivity(i);
            //------------------------------------------

        }else if (id == R.id.nav_settings) {
            // ----------------------
            Intent i = new Intent(SleepActivity.this, BasicInputActivity.class);
            startActivity(i);
            //------------------------------------------
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

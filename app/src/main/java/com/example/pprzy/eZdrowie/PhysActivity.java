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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pprzy.eZdrowie.model.Phys;

import com.example.pprzy.eZdrowie.sqlite.MySQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PhysActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btn_zatwierdz, btn_pokaz, btn_update, btn_usun;
    //EditText et_puls, et_cs, et_cr, et_puls_fromdate, et_cs_fromdate, et_cr_fromdate;
    String typed_scale, typed_walk, typed_breaks, typed_adta;
    TextView tv_adta, txtProfileName;


    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = sdf.format(c.getTime());
    SimpleDateFormat sdfFromDatePicker = new SimpleDateFormat( "yyyy-MM-dd");
    String formattedDateFromDatePicker;
    int year;
    int month;
    int day;
    DatePicker datePicker;
    boolean phys_exist;

    RadioButton rbS1, rbS2, rbS3, rbS4, rbS5, rbW1, rbW2, rbW3, rbB1, rbB2, rbB3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phys);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        String[] elementy = {"Brak dodatkowej aktywności", "Bieganie", "Jazda na rowerze", "Pływanie", "Siłownia/fitnes", "Sport drużynowy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elementy);
        final Spinner spinner = (Spinner)findViewById(R.id.spinner1);
        spinner.setAdapter(adapter);



        //add to data bsse

        final MySQLiteHelper db = new MySQLiteHelper(this);

        btn_zatwierdz = (Button) findViewById(R.id.button_confirm_notification1);
        rbS1 = (RadioButton) findViewById(R.id.ocena1);
        rbS2 = (RadioButton) findViewById(R.id.ocena2);
        rbS3 = (RadioButton) findViewById(R.id.ocena3);
        rbS4 = (RadioButton) findViewById(R.id.ocena4);
        rbS5 = (RadioButton) findViewById(R.id.ocena5);

        rbW1 = (RadioButton) findViewById(R.id.time1);
        rbW2 = (RadioButton) findViewById(R.id.time2);
        rbW3 = (RadioButton) findViewById(R.id.time3);

        rbB1 = (RadioButton) findViewById(R.id.time_1);
        rbB2 = (RadioButton) findViewById(R.id.time_2);
        rbB3 = (RadioButton) findViewById(R.id.time_3);

        tv_adta = (TextView) findViewById(R.id.textViewPhysOther);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner1);

        btn_zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //radiobuttons

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

                //walk

                if(rbW1.isChecked()==true) {
                    typed_walk = "<20";
                }
                if(rbW2.isChecked()==true) {
                    typed_walk = "20-60";
                }
                if(rbW3.isChecked()==true) {
                    typed_walk = ">60";
                }

                //breaks
                if(rbB1.isChecked()==true) {
                    typed_breaks = "Brak";
                }
                if(rbB2.isChecked()==true) {
                    typed_breaks = "Jedna";
                }
                if(rbB3.isChecked()==true) {
                    typed_breaks = "Więcej niż jedna";
                }



                typed_adta = spinner2.getSelectedItem().toString();

                //------
                phys_exist = db.isExistingPhys(formattedDate);
                if (phys_exist == false) {

                    db.addPhys(new Phys(formattedDate,typed_scale, typed_walk,typed_breaks,typed_adta));
                    Toast.makeText(PhysActivity.this,
                            "Pomyślnie zapisano!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PhysActivity.this,
                            "Twoja dzisiejsza aktowność została już zapisana.", Toast.LENGTH_LONG).show();
                }}
        });


//show by date
        btn_pokaz = (Button) findViewById(R.id.button_pokaz_phys);



        btn_pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----
                datePicker = (DatePicker) findViewById(R.id.datePickerPhys);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());


                String str_scale = db.getSCPByDate(formattedDateFromDatePicker);
                TextView tv_scale_fromdate = (TextView) findViewById(R.id.textViewPhysScaleFromDate);
                tv_scale_fromdate.setText(tv_scale_fromdate.getText()+str_scale);

                String str_walk = db.getWalkByDate(formattedDateFromDatePicker);
                TextView tv_walk_fromdate = (TextView) findViewById(R.id.textViewPhysTime1FromDate);
                tv_walk_fromdate.setText(tv_walk_fromdate.getText()+str_walk);

                String str_breaks = db.getBreaksByDate(formattedDateFromDatePicker);
                TextView tv_breaks_fromdate = (TextView) findViewById(R.id.textViewPhysTime2FromDate);
                tv_breaks_fromdate.setText(tv_breaks_fromdate.getText()+str_breaks);

                String str_other = db.getADTAByDate(formattedDateFromDatePicker);
                TextView tv_other_fromdate = (TextView) findViewById(R.id.textViewPhysOtherFromDate);
                Spinner spinner_from_date = (Spinner) findViewById(R.id.spinner1);
                str_other = spinner_from_date.getSelectedItem().toString();
                tv_other_fromdate.setText(tv_other_fromdate.getText()+str_other);

                Toast.makeText(PhysActivity.this,
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
        getMenuInflater().inflate(R.menu.phys, menu);
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
            Intent i = new Intent(PhysActivity.this, MainActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_weight) {

            //------------------------------------------
            Intent i = new Intent(PhysActivity.this, WeightActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_hpressure) {

            Intent i = new Intent(PhysActivity.this, PulseActivity.class);
            startActivity(i);
            //---

        } else if (id == R.id.nav_sleep) {

            // ----------------------
            Intent i = new Intent(PhysActivity.this, SleepActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notes) {

            // ----------------------
            Intent i = new Intent(PhysActivity.this, NotesActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notifications) {

            // ----------------------
            Intent i = new Intent(PhysActivity.this, NotificationsActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(PhysActivity.this, BasicInputActivity.class);
            startActivity(i);
            //------------------------------------------
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

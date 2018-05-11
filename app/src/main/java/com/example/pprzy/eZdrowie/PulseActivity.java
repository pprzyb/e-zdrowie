package com.example.pprzy.eZdrowie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pprzy.eZdrowie.model.Puls;
import com.example.pprzy.eZdrowie.sqlite.MySQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PulseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button btn_zatwierdz, btn_pokaz, btn_update, btn_usun;
    EditText et_puls, et_cs, et_cr, et_puls_fromdate, et_cs_fromdate, et_cr_fromdate;
    String typed_puls, typed_cs, typed_cr, str_puls, str_cs, str_cr;
    TextView txtProfileName;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = sdf.format(c.getTime());
    String data_puls_do_edycji;
    SimpleDateFormat sdfFromDatePicker = new SimpleDateFormat( "yyyy-MM-dd");
    String formattedDateFromDatePicker;
    int year;
    int month;
    int day;
    DatePicker datePicker;
    boolean puls_exist;

    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    FrameLayout frameLayout1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        showPopupBtn = (Button) findViewById(R.id.button_wiecej);
        frameLayout1 = (FrameLayout) findViewById(R.id.layout_cisnienie);



        frameLayout1.getForeground().setAlpha( 0); // restore

        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) PulseActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.poput_cisnienie,null);

                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                frameLayout1.getForeground().setAlpha( 180); // restore

                //instantiate popup window
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(frameLayout1, Gravity.CENTER, 0, 0);


                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        frameLayout1.getForeground().setAlpha( 0); // restore


                    }
                });

            }
        });


//add to data base

        final MySQLiteHelper db = new MySQLiteHelper(this);

        btn_zatwierdz = (Button) findViewById(R.id.button_zatwierdz_cisnienie);
        et_puls = (EditText) findViewById(R.id.editText_puls);
        et_cs = (EditText) findViewById(R.id.editText_cisnienie_skurczowe);
        et_cr = (EditText) findViewById(R.id.editText_cisnienie_rozkurczowe);



        btn_zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puls_exist = db.isExistingPuls(formattedDate);
                if (puls_exist == false) {
                    typed_puls = (String) et_puls.getText().toString();
                    typed_cs = (String) et_cs.getText().toString();
                    typed_cr = (String) et_cr.getText().toString();
                    db.addPuls(new Puls(formattedDate,typed_puls,typed_cs,typed_cr));
                    Toast.makeText(PulseActivity.this,
                            "Pomyślnie zapisano!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PulseActivity.this,
                            "Dzisiejsze pomary zostały już wprowadzone. Wyświetl je i edytuj bądź usuń.", Toast.LENGTH_LONG).show();
                }}
        });



        //show by date

        btn_pokaz = (Button) findViewById(R.id.button_pokaz_dane_puls);



        btn_pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----
                datePicker = (DatePicker) findViewById(R.id.datePickerPulse);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());


                str_puls = db.getPulsByDate(formattedDateFromDatePicker);
                et_puls_fromdate = (EditText) findViewById(R.id.pulszdnia);
                et_puls_fromdate.setText(str_puls);

                str_cs = db.getCSByDate(formattedDateFromDatePicker);
                et_cs_fromdate = (EditText) findViewById(R.id.cisnieniezdnia);
                et_cs_fromdate.setText(str_cs);

                str_cr = db.getCRByDate(formattedDateFromDatePicker);
                et_cr_fromdate = (EditText) findViewById(R.id.cisnienie_rozkurcz_zdnia);
                et_cr_fromdate.setText(str_cr);

                Toast.makeText(PulseActivity.this,
                        "Wyświetlono dane", Toast.LENGTH_SHORT).show();
            }
        });


        //data update
        btn_update = (Button) findViewById(R.id.button_update_puls);



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----uzyskanie daty w formacie zdatnym do zapisu w bazie
                datePicker = (DatePicker) findViewById(R.id.datePickerPulse);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());

                //update wagi

                et_puls_fromdate = (EditText) findViewById(R.id.pulszdnia);
                str_puls = et_puls_fromdate.getText().toString();
                db.updatePuls(formattedDateFromDatePicker,str_puls);

                et_cs_fromdate = (EditText) findViewById(R.id.cisnieniezdnia);
                str_cs = et_cs_fromdate.getText().toString();
                db.updateCS(formattedDateFromDatePicker,str_cs);

                et_cr_fromdate = (EditText) findViewById(R.id.cisnienie_rozkurcz_zdnia);
                str_cr = et_cr_fromdate.getText().toString();
                db.updateCR(formattedDateFromDatePicker,str_cr);

                Toast.makeText(PulseActivity.this,
                        "Zaktualizowano dane", Toast.LENGTH_SHORT).show();
            }
        });


//note delete
        btn_usun = (Button) findViewById(R.id.button_usun_puls);



        btn_usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----uzyskanie daty jako string w formacie sdf
                datePicker = (DatePicker) findViewById(R.id.datePickerPulse);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());

                //update notatki

                et_puls_fromdate = (EditText) findViewById(R.id.pulszdnia);
                str_puls = et_puls_fromdate.getText().toString();
                data_puls_do_edycji = db.getIdByDate(formattedDateFromDatePicker);

                db.deletePuls(formattedDateFromDatePicker);


                Toast.makeText(PulseActivity.this,
                        "Usunięto dane", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.pulse, menu);
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
            Intent i = new Intent(PulseActivity.this, MainActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_weight) {

            //------------------------------------------
            Intent i = new Intent(PulseActivity.this, WeightActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_hpressure) {


        } else if (id == R.id.nav_sleep) {

            // ----------------------
            Intent i = new Intent(PulseActivity.this, SleepActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_daily_activity) {

            // ----------------------
            Intent i = new Intent(PulseActivity.this, PhysActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notes) {

            // ----------------------
            Intent i = new Intent(PulseActivity.this, NotesActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notifications) {

            // ----------------------
            Intent i = new Intent(PulseActivity.this, NotificationsActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_settings) {
            // ----------------------
            Intent i = new Intent(PulseActivity.this, BasicInputActivity.class);
            startActivity(i);
            //------------------------------------------
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.widget.Button;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pprzy.eZdrowie.model.Weight;
import com.example.pprzy.eZdrowie.sqlite.MySQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class WeightActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button btn_zatwierdz, btn_pokaz, btn_update, btn_usun;
    EditText et_weight, et_weight_fromdate;
    String typed_weight, str_weight;
    TextView txtProfileName;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = sdf.format(c.getTime());
    String data_wagi_do_edycji;
    float bmi = 0, wagaBmi, wzrostBmi, wzrost_float=0;;

    SimpleDateFormat sdfFromDatePicker = new SimpleDateFormat( "yyyy-MM-dd");
    String formattedDateFromDatePicker;
    int year;
    int month;
    int day;
    DatePicker datePicker;
    boolean weight_exist;

    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    FrameLayout frameLayout1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        showPopupBtn = (Button) findViewById(R.id.showPopupBtn);
        frameLayout1 = (FrameLayout) findViewById(R.id.layout_waga);



        frameLayout1.getForeground().setAlpha( 0); // restore

        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) WeightActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup_bmi,null);

                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                frameLayout1.getForeground().setAlpha( 180); // restore

                //instantiate popup window
                popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

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




        //add to database

        final MySQLiteHelper db = new MySQLiteHelper(this);

        btn_zatwierdz = (Button) findViewById(R.id.editText_puls);
        et_weight = (EditText) findViewById(R.id.editText_dzienna_waga);
        final TextView tvBMI = (TextView) findViewById(R.id.textView_bmi_obliczone);

        btn_zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight_exist = db.isExistingWeight(formattedDate);
                if (weight_exist == false) {
                    typed_weight = (String) et_weight.getText().toString();
                    db.addWeight(new Weight(formattedDate, typed_weight));
                    Toast.makeText(WeightActivity.this,
                            "Pomyślnie zapisano wagę", Toast.LENGTH_SHORT).show();
                    wagaBmi = Float.parseFloat(typed_weight);
                    wzrostBmi = db.getHeightBasic();
                    bmi= ((wagaBmi/(wzrostBmi*wzrostBmi))*10000);
                    String formattedString = String.format("%.02f", bmi);
                    tvBMI.setText(formattedString);
                }
                else{
                    Toast.makeText(WeightActivity.this,
                            "Dzisiejsza waga została już zapisana. Wyświetl ją i edytuj bądź usuń.", Toast.LENGTH_LONG).show();
                    String waga_else = db.getWeightByDate(formattedDate);
                    //typed_weight = (String) et_weight.getText().toString();
                    wzrost_float = Float.parseFloat(waga_else);
                    wagaBmi = Float.parseFloat(waga_else);
                    wzrostBmi = db.getHeightBasic();
                    bmi= ((wagaBmi/(wzrostBmi*wzrostBmi))*10000);
                    String formattedString = String.format("%.02f", bmi);
                    tvBMI.setText(formattedString);
                }}
        });


//show by date

        btn_pokaz = (Button) findViewById(R.id.button_pokaz_wage);



        btn_pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----
                datePicker = (DatePicker) findViewById(R.id.datePickerWeight);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());


                str_weight = db.getWeightByDate(formattedDateFromDatePicker);
                et_weight_fromdate = (EditText) findViewById(R.id.waga_z_dnia);

                et_weight_fromdate.setText(str_weight);

                Toast.makeText(WeightActivity.this,
                        "Wyświetlono wagę", Toast.LENGTH_SHORT).show();
            }
        });


        //weight update

        btn_update = (Button) findViewById(R.id.button_zmien_wage);



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----uzyskanie daty w formacie zdatnym do zapisu w bazie
                datePicker = (DatePicker) findViewById(R.id.datePickerWeight);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());

                //update wagi

                et_weight_fromdate = (EditText) findViewById(R.id.waga_z_dnia);
                str_weight = et_weight_fromdate.getText().toString();

                db.updateWeight(formattedDateFromDatePicker,str_weight);



                Toast.makeText(WeightActivity.this,
                        "Zaktualizowano wagę", Toast.LENGTH_SHORT).show();
            }
        });


//weight delete

        btn_usun = (Button) findViewById(R.id.button_usun_wage);


        btn_usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----uzyskanie daty jako string w formacie sdf
                datePicker = (DatePicker) findViewById(R.id.datePickerWeight);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());

                //update notatki

                et_weight_fromdate = (EditText) findViewById(R.id.editText_dzienna_waga);
                str_weight = et_weight_fromdate.getText().toString();
                data_wagi_do_edycji = db.getIdByDate(formattedDateFromDatePicker);

                db.deleteWeight(formattedDateFromDatePicker);


                Toast.makeText(WeightActivity.this,
                        "Usunięto zapisaną wagę", Toast.LENGTH_SHORT).show();
            }
        });


//BMI  calculate

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
        getMenuInflater().inflate(R.menu.weight, menu);
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

            // ----------------------
            Intent i = new Intent(WeightActivity.this, MainActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_weight) {



        } else if (id == R.id.nav_hpressure) {

            //----------
            Intent i = new Intent(WeightActivity.this, PulseActivity.class);
            startActivity(i);
            //---

        } else if (id == R.id.nav_sleep) {

            // ----------------------
            Intent i = new Intent(WeightActivity.this, SleepActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_daily_activity) {

            // ----------------------
            Intent i = new Intent(WeightActivity.this, PhysActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notes) {

            // ----------------------
            Intent i = new Intent(WeightActivity.this, NotesActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notifications) {

            // ----------------------
            Intent i = new Intent(WeightActivity.this, NotificationsActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_settings) {
            // ----------------------
            Intent i = new Intent(WeightActivity.this, BasicInputActivity.class);
            startActivity(i);
            //------------------------------------------
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

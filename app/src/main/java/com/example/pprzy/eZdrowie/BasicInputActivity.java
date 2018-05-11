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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pprzy.eZdrowie.model.Basic;

import com.example.pprzy.eZdrowie.sqlite.MySQLiteHelper;

public class BasicInputActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtProfileName;
    Button btn_save, btn_update;
    EditText et_name, et_age, et_height, et_weight;
    String typed_name, typed_age, typed_gender, typed_height, typed_weight;
    RadioButton rb_gender_female, rb_gender_male;
    boolean basic_exist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //save basic informations in database
        final MySQLiteHelper db = new MySQLiteHelper(this);

        btn_save = (Button) findViewById(R.id.button_confirm_notification1);
        et_name = (EditText) findViewById(R.id.editTextFirstName);
        et_age = (EditText) findViewById(R.id.editTextAge);
        et_height = (EditText) findViewById(R.id.editTextHeight);
        et_weight = (EditText) findViewById(R.id.editTextWeight);
        rb_gender_female = (RadioButton) findViewById(R.id.radioFemale);
        rb_gender_male = (RadioButton) findViewById(R.id.radioMale);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basic_exist = db.isExistingBasic();

                if (basic_exist == true) {
                    typed_name = (String) et_name.getText().toString();
                    typed_age = (String) et_age.getText().toString();

                    if(rb_gender_female.isChecked()==true) {
                        typed_gender = "K";
                    }
                    if(rb_gender_male.isChecked()==true) {
                        typed_gender = "M";
                    }

                    typed_height = (String) et_height.getText().toString();
                    typed_weight = (String) et_weight.getText().toString();


                    db.addBasic(new Basic(typed_name,typed_age,typed_gender,typed_height,typed_weight));
                    Toast.makeText(BasicInputActivity.this,
                            "Pomyślnie zapisano podstawowe dane!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(BasicInputActivity.this,
                            "Podstawowe dane zostały już zapisane. Możesz je zmienić klikając przycisk ZAKTUALIZUJ", Toast.LENGTH_LONG).show();
                }
            }
        });

//updating basic data

        btn_update = (Button) findViewById(R.id.button_aktualizuj_basic);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    typed_name = (String) et_name.getText().toString();
                    typed_age = (String) et_age.getText().toString();

                    if(rb_gender_female.isChecked()==true) {
                        typed_gender = "K";
                    }
                    if(rb_gender_male.isChecked()==true) {
                        typed_gender = "M";
                    }

                    typed_height = (String) et_height.getText().toString();
                    typed_weight = (String) et_weight.getText().toString();

                    db.updateBasic(typed_name,typed_age,typed_gender,typed_height,typed_weight);

                    Toast.makeText(BasicInputActivity.this,
                            "Pomyślnie zaktualizowano podstawowe dane!", Toast.LENGTH_SHORT).show();
                }
           // }
        });


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
        getMenuInflater().inflate(R.menu.basic_input, menu);
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
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(BasicInputActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_weight) {
            Intent i = new Intent(BasicInputActivity.this, WeightActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_hpressure) {
            Intent i = new Intent(BasicInputActivity.this, PulseActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_sleep) {
            Intent i = new Intent(BasicInputActivity.this, SleepActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_daily_activity) {
            Intent i = new Intent(BasicInputActivity.this, PhysActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_notes) {
            Intent i = new Intent(BasicInputActivity.this, NotesActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_notifications) {
            Intent i = new Intent(BasicInputActivity.this, NotificationsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_settings) {
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

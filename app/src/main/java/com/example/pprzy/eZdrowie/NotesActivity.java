//kolumna DATE to notatka, a NOTE to data. Pomyłka w początkowym stadium tworzenia bazy, obecnie zbyt duży problem z refaktoryzacją

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//-----------------------------------
import com.example.pprzy.eZdrowie.model.Note;
import com.example.pprzy.eZdrowie.sqlite.MySQLiteHelper;
//--------------------

//CCCCCCCCCCCCCCCCCCC
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


public class NotesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btn_zatwierdz, btn_show_data, btn_update, button_del;
    EditText et_notatka, et_note_from_date;
    TextView tv_notatka, txtProfileName;
    String typed_text, str_note;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = sdf.format(c.getTime());
    Note pobrana_notatka;
    String data_notatki_do_edycji;
    int id_z_daty;

    SimpleDateFormat sdfFromDatePicker = new SimpleDateFormat( "yyyy-MM-dd");
    Date dtFromDatePicker;
    String formattedDateFromDatePicker;
    int year;
    int month;
    int day;
    DatePicker datePicker;
    boolean note_exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//add to data base

        final MySQLiteHelper db = new MySQLiteHelper(this);

            btn_zatwierdz = (Button) findViewById(R.id.button_zatwierdz);
            et_notatka = (EditText) findViewById(R.id.editText_notes);

            btn_zatwierdz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note_exist = db.isExisting(formattedDate);
                if (note_exist == false) {
                    typed_text = (String) et_notatka.getText().toString();
                    db.addNote(new Note(typed_text, formattedDate));
                    Toast.makeText(NotesActivity.this,
                             "Pomyślnie dodano notatkę", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NotesActivity.this,
                            "Notatka z tego dnia istnieje. Wyświetl ją i edytuj bądź usuń.", Toast.LENGTH_LONG).show();
                }}
            });

//showing notes from picked date
        btn_show_data = (Button) findViewById(R.id.button_show_note);
        btn_show_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePicker = (DatePicker) findViewById(R.id.datePickerNotes);
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());

                str_note = db.getNoteByDate(formattedDateFromDatePicker);

                et_note_from_date = (EditText) findViewById(R.id.et_notatka_z_dnia);
                et_note_from_date.setText(str_note);

                Toast.makeText(NotesActivity.this,
                        "Wyświetlono notatkę", Toast.LENGTH_SHORT).show();
            }
        });

//update of note

        btn_update = (Button) findViewById(R.id.button_edytuj_notatke);



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----uzyskanie daty w formacie zdatnym do zapisu w bazie
                datePicker = (DatePicker) findViewById(R.id.datePickerNotes);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());

                //update notatki

                et_note_from_date = (EditText) findViewById(R.id.et_notatka_z_dnia);
                str_note = et_note_from_date.getText().toString();

                db.updateNote2(formattedDateFromDatePicker, str_note);



                Toast.makeText(NotesActivity.this,
                        "Zaktualizowano notatkę", Toast.LENGTH_SHORT).show();
            }
        });

//delete of note

        button_del = (Button) findViewById(R.id.button_usun_notatke);



        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-----get date as a String in Single Date Format
                datePicker = (DatePicker) findViewById(R.id.datePickerNotes);

                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                formattedDateFromDatePicker = sdfFromDatePicker.format(calendar.getTime());

                //note update

                et_note_from_date = (EditText) findViewById(R.id.et_notatka_z_dnia);
                str_note = et_note_from_date.getText().toString();
                data_notatki_do_edycji = db.getIdByDate(formattedDateFromDatePicker);

                db.deleteNote2(formattedDateFromDatePicker);


                Toast.makeText(NotesActivity.this,
                        "Usunięto notatkę", Toast.LENGTH_SHORT).show();
            }
        });

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
        getMenuInflater().inflate(R.menu.notes, menu);
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
            Intent i = new Intent(NotesActivity.this, MainActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_weight) {

            //------------------------------------------
            Intent i = new Intent(NotesActivity.this, WeightActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_hpressure) {

            Intent i = new Intent(NotesActivity.this, PulseActivity.class);
            startActivity(i);
            //---

        } else if (id == R.id.nav_sleep) {

            // ----------------------
            Intent i = new Intent(NotesActivity.this, SleepActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_daily_activity) {

            // ----------------------
            Intent i = new Intent(NotesActivity.this, PhysActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notes) {

            // ----------------------
            Intent i = new Intent(NotesActivity.this, NotesActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_notifications) {

            // ----------------------
            Intent i = new Intent(NotesActivity.this, NotificationsActivity.class);
            startActivity(i);
            //------------------------------------------

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

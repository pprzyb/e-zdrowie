//kolumna DATE to notatka, a NOTE to data. Pomyłka w początkowym stadium tworzenia bazy, obecnie zbyt duży problem z refaktoryzacją


package com.example.pprzy.eZdrowie.sqlite;

import com.example.pprzy.eZdrowie.model.Note;
import com.example.pprzy.eZdrowie.model.Puls;
import com.example.pprzy.eZdrowie.model.Weight;
import com.example.pprzy.eZdrowie.model.Basic;
import com.example.pprzy.eZdrowie.model.Sleep;
import com.example.pprzy.eZdrowie.model.Phys;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ValuesDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create note table
        String CREATE_NOTES_TABLE = "CREATE TABLE notes ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "note TEXT, " +
                "date TEXT )";
        //SQL statement to create weight table
        String CREATE_WEIGHT_TABLE = "CREATE TABLE weight ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "weight TEXT, " +
                "date TEXT )";
        //SQL statement to create puls table
        String CREATE_PULS_TABLE = "CREATE TABLE puls ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "puls TEXT, " +
                "cis_skurcz TEXT, " +
                "cis_rozkurcz TEXT, " +
                "date TEXT )";
        //SQL statement to create basic data table
        String CREATE_BASIC_TABLE = "CREATE TABLE basic ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "age TEXT, " +
                "gender TEXT, " +
                "height TEXT, " +
                "weight_basic TEXT )";
        //SQL statement to create sleep table
        String CREATE_SLEEP_TABLE = "CREATE TABLE sleep ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "scale TEXT, " +
                "timeToGetSleep TEXT, " +
                "timeOfSleep TEXT, " +
                "numberOfAwakes TEXT, " +
                "date TEXT )";
        //SQL statement to create phys table
        String CREATE_PHYS_TABLE = "CREATE TABLE phys ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "scale_phys TEXT, " +
                "walk TEXT, " +
                "breaks TEXT, " +
                "adtActivity TEXT, " +
                "date TEXT )";
        // create tablea
        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_WEIGHT_TABLE);
        db.execSQL(CREATE_PULS_TABLE);
        db.execSQL(CREATE_BASIC_TABLE);
        db.execSQL(CREATE_SLEEP_TABLE);
        db.execSQL(CREATE_PHYS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older notes table if existed
        db.execSQL("DROP TABLE IF EXISTS notes");
        //weight
        db.execSQL("DROP TABLE IF EXISTS weight");
        //puls
        db.execSQL("DROP TABLE IF EXISTS puls");
        //basic
        db.execSQL("DROP TABLE IF EXISTS basic");
        //sleep
        db.execSQL("DROP TABLE IF EXISTS sleep");
        //sleep
        db.execSQL("DROP TABLE IF EXISTS phys");
        // create fresh note table
        this.onCreate(db);
    }

    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) note + get all note + delete all notes
     */

    // Notes table name
    private static final String TABLE_NOTES = "notes";
    //weight
    private static final String TABLE_WEIGHT = "weight";
    //puls
    private static final String TABLE_PULS = "puls";
    //basic
    private static final String TABLE_BASIC = "basic";

    private static final String TABLE_SLEEP_TABLE = "sleep";

    private static final String TABLE_PHYS = "phys";

    // Notes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_NOTE = "note";

    private static final String[] COLUMNS = {KEY_ID, KEY_DATE, KEY_NOTE};

    //weight
    private static final String KEY_ID_WEIGHT = "id";
    private static final String KEY_DATE_WEIGHT = "date";
    private static final String KEY_WEIGHT = "weight";

    private static final String[] COLUMNS_WEIGHT = {KEY_ID_WEIGHT, KEY_DATE_WEIGHT, KEY_WEIGHT};

    //puls
    private static final String KEY_ID_PULS = "id";
    private static final String KEY_DATE_PULS = "date";
    private static final String KEY_PULS = "puls";
    private static final String KEY_CIS_SKURCZ = "cis_skurcz";
    private static final String KEY_CIS_ROZKURCZ = "cis_rozkurcz";

    private static final String[] COLUMNS_PULS = {KEY_ID_PULS, KEY_DATE_PULS, KEY_PULS, KEY_CIS_SKURCZ, KEY_CIS_ROZKURCZ};

    //puls
    private static final String KEY_ID_BASIC = "id";
    private static final String KEY_DATE_BASIC = "date";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_BASIC_WEIGHT = "weight_basic";

    private static final String[] COLUMNS_BASIC = {KEY_ID_BASIC, KEY_DATE_BASIC, KEY_NAME, KEY_AGE, KEY_GENDER, KEY_HEIGHT, KEY_BASIC_WEIGHT};

    //sleep
    private static final String KEY_ID_SLEEP = "id";
    private static final String KEY_DATE_SLEEP = "date";
    private static final String KEY_SCALE = "scale";
    private static final String KEY_TTGS = "timeToGetSleep";
    private static final String KEY_TOS = "timeOfSleep";
    private static final String KEY_NOA = "numberOfAwakes";

    private static final String[] COLUMNS_SLEEP = {KEY_ID_SLEEP, KEY_DATE_SLEEP, KEY_SCALE, KEY_TTGS, KEY_TOS, KEY_NOA};

    //phys
    private static final String KEY_ID_PHYS = "id";
    private static final String KEY_DATE_PHYS = "date";
    private static final String KEY_SCALE_PHYS = "scale_phys";
    private static final String KEY_WALK = "walk";
    private static final String KEY_BREAKS = "breaks";
    private static final String KEY_ADTACTIVITY = "adtActivity";

    private static final String[] COLUMNS_PHYS = {KEY_ID_PHYS, KEY_DATE_PHYS, KEY_SCALE_PHYS, KEY_WALK, KEY_BREAKS, KEY_ADTACTIVITY};


    //saving the note
    public void addNote(Note note) {
        Log.d("addNote", note.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, note.getDate()); // get date
        values.put(KEY_NOTE, note.getNote()); // get note

        // 3. insert
        db.insert(TABLE_NOTES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


    //is specific date note exist
    public boolean isExisting(String formatted_date) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query_check_exist = "SELECT * from notes where note = '" + formatted_date + "'";
        Cursor cursor = db.rawQuery(query_check_exist, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }



    //get note by date
    public String getNoteByDate(String data_notatki) {

        Cursor cursor = null;
        String notatka_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT date FROM notes WHERE note = '" + data_notatki + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                notatka_z_dnia = cursor.getString(cursor.getColumnIndex("date"));
            }
            return notatka_z_dnia;
        } finally {
            cursor.close();
        }
    }



    //get id by date
    public String getIdByDate(String data_notatki) {

        Cursor cursor = null;
        //String notatka_z_dnia = new String();
        String id_z_daty = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT id FROM notes WHERE note = '" + data_notatki + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                id_z_daty = cursor.getString(cursor.getColumnIndex("id"));
            }
            return id_z_daty;
        } finally {
            cursor.close();
        }
    }



    // Updating single note
    public void updateNote2(String data, String notatka) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        String query_string = ("UPDATE notes SET date = '" + notatka + "' WHERE note = '" + data + "';");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } finally {
            cursor.close();
        }

    }



    // Deleting single note
    public void deleteNote2(String data) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = null;

        String query_string = ("DELETE FROM notes WHERE note = '" + data + "';");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                //id_dt = cursor.getString(cursor.getColumnIndex("id"));
            }
        } finally {
            cursor.close();
        }

    }




    //Weight table
    public void addWeight(Weight weight) {
        Log.d("addWeight", weight.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();


        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_WEIGHT, weight.getDate()); // get date
        values.put(KEY_WEIGHT, weight.getWeight()); // get weight

        // 3. insert
        db.insert(TABLE_WEIGHT, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


    //is weight with specific date exist
    public boolean isExistingWeight(String formatted_date) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query_check_exist = "SELECT * FROM weight WHERE date = '" + formatted_date + "'";
        Cursor cursor = db.rawQuery(query_check_exist, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    //get weight by date
    public String getWeightByDate(String data_wagi) {

        Cursor cursor = null;
        String waga_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT weight FROM weight WHERE date = '" + data_wagi + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                waga_z_dnia = cursor.getString(cursor.getColumnIndex("weight"));
            }
            return waga_z_dnia;
        } finally {
            cursor.close();
        }
    }

    // Updating single weight
    public void updateWeight(String data, String waga) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        String query_string = ("UPDATE weight SET weight = '" + waga + "' WHERE date = '" + data + "';");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } finally {
            cursor.close();
        }

    }


    // Deleting single weight
    public void deleteWeight(String data) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = null;

        String query_string = ("DELETE FROM weight WHERE date = '" + data + "';");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                //id_dt = cursor.getString(cursor.getColumnIndex("id"));
            }
        } finally {
            cursor.close();
        }

    }

//pulse
public void addPuls(Puls puls) {
    Log.d("addPuls", puls.toString());
    // 1. get reference to writable DB
    SQLiteDatabase db = this.getWritableDatabase();


    // 2. create ContentValues to add key "column"/value
    ContentValues values = new ContentValues();
    values.put(KEY_DATE_PULS, puls.getDate()); // get date
    values.put(KEY_PULS, puls.getPuls()); // get puls
    values.put(KEY_CIS_SKURCZ, puls.getCisSkurcz()); // get cisnienie skurczowe
    values.put(KEY_CIS_ROZKURCZ, puls.getCisRozkurcz()); //get cisnienie rozkurczowe

    // 3. insert
    db.insert(TABLE_PULS, // table
            null, //nullColumnHack
            values); // key/value -> keys = column names/ values = column values

    // 4. close
    db.close();
}

    //is pulse with specific date exist
    public boolean isExistingPuls(String formatted_date) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query_check_exist = "SELECT * FROM puls WHERE date = '" + formatted_date + "'";
        Cursor cursor = db.rawQuery(query_check_exist, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }



    public String getPulsByDate(String data_puls) {

        Cursor cursor = null;
        String puls_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT puls FROM puls WHERE date = '" + data_puls + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                puls_z_dnia = cursor.getString(cursor.getColumnIndex("puls"));
            }
            return puls_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //----- GET CS BY DATE
    public String getCSByDate(String data_puls) {

        Cursor cursor = null;
        String cs_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT cis_skurcz FROM puls WHERE date = '" + data_puls + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                cs_z_dnia = cursor.getString(cursor.getColumnIndex("cis_skurcz"));
            }
            return cs_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //----- GET CR BY DATE
    public String getCRByDate(String data_puls) {

        Cursor cursor = null;
        String cr_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT cis_rozkurcz FROM puls WHERE date = '" + data_puls + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                cr_z_dnia = cursor.getString(cursor.getColumnIndex("cis_rozkurcz"));
            }
            return cr_z_dnia;
        } finally {
            cursor.close();
        }
    }

    // Updating single pulse
    public void updatePuls(String data, String puls) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        String query_string = ("UPDATE puls SET puls = '" + puls + "' WHERE date = '" + data + "';");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } finally {
            cursor.close();
        }

    }


    // Updating single cs
    public void updateCS(String data, String cs) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        String query_string = ("UPDATE puls SET cis_skurcz = '" + cs + "' WHERE date = '" + data + "';");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } finally {
            cursor.close();
        }

    }


    // Updating single cr
    public void updateCR(String data, String cr) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        String query_string = ("UPDATE puls SET cis_rozkurcz = '" + cr + "' WHERE date = '" + data + "';");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } finally {
            cursor.close();
        }

    }


    // Deleting single weight
    public void deletePuls(String data) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = null;

        String query_string = ("DELETE FROM puls WHERE date = '" + data + "';");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                //id_dt = cursor.getString(cursor.getColumnIndex("id"));
            }
        } finally {
            cursor.close();
        }

    }


//--------BasicInput DATA

    //add basic data
    public void addBasic(Basic basic) {
        Log.d("addBasic", basic.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();


        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
       // values.put(KEY_DATE_BASIC, basic.getDate()); // get date
        values.put(KEY_NAME, basic.getName()); // get name
        values.put(KEY_AGE, basic.getAge()); // get age
        values.put(KEY_GENDER, basic.getGender()); //get gender
        values.put(KEY_HEIGHT, basic.getHeight()); //get height
        values.put(KEY_BASIC_WEIGHT, basic.getWeightBasic()); //get gender

        // 3. insert
        db.insert(TABLE_BASIC, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


    //----- is basic data exist
    public boolean isExistingBasic() {

        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM basic";
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        boolean is_existing;
        if (icount > 0) {
            is_existing = false;}
        else{
            is_existing = true;
            }
        return is_existing;
    }

    public void update() {

    }

//----- GET HEIGHT
public float getHeightBasic() {

    Cursor cursor = null;
    String wzrostBasicSTR = new String();
    float wzrostBasic=0;

    // 1. get reference to readable DB
    SQLiteDatabase db = this.getReadableDatabase();

    try {
        cursor = db.rawQuery("SELECT height FROM basic WHERE id = 1", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            wzrostBasicSTR = cursor.getString(cursor.getColumnIndex("height"));
            wzrostBasic = Float.parseFloat(wzrostBasicSTR);
        }
        return wzrostBasic;
    } finally {
        cursor.close();
    }
}

    //----- GET NAME
    public String getNameBasic() {

        Cursor cursor = null;
        String name = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT name FROM basic WHERE id = 1", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                name = cursor.getString(cursor.getColumnIndex("name"));
            }
            return name;
        } finally {
            cursor.close();
        }
    }

    // Updating
    public void updateBasic(String name, String age, String gender, String heigh, String weight_basic) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        String query_string = ("UPDATE basic SET name = '" + name + "', age = '" + age + "', gender = '" + gender + "', height = '" + heigh + "', weight_basic = '" + weight_basic + "' WHERE ID = 1;");


        // 3. updating row
        try {
            cursor = db.rawQuery(query_string, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } finally {
            cursor.close();
        }

    }

//------SLEEP
//add basic data
public void addSleep(Sleep sleep) {
    Log.d("addSleep", sleep.toString());
    // 1. get reference to writable DB
    SQLiteDatabase db = this.getWritableDatabase();


    // 2. create ContentValues to add key "column"/value
    ContentValues values = new ContentValues();
    values.put(KEY_DATE_SLEEP, sleep.getDate());
    values.put(KEY_SCALE, sleep.getScale());
    values.put(KEY_TTGS, sleep.getTimeToGetSleep());
    values.put(KEY_TOS, sleep.getTimeOfSleep());
    values.put(KEY_NOA, sleep.getNumberOfAwakes());


    // 3. insert
    db.insert(TABLE_SLEEP_TABLE, // table
            null, //nullColumnHack
            values); // key/value -> keys = column names/ values = column values

    // 4. close
    db.close();
}

    //----- is sleep with specific date exist
    public boolean isExistingSleep(String formatted_date) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query_check_exist = "SELECT * FROM sleep WHERE date = '" + formatted_date + "'";
        Cursor cursor = db.rawQuery(query_check_exist, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    //----- GET SC BY DATE
    public String getSCByDate(String data_sleep) {

        Cursor cursor = null;
        String sc_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT scale FROM sleep WHERE date = '" + data_sleep + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sc_z_dnia = cursor.getString(cursor.getColumnIndex("scale"));
            }
            return sc_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //----- GET TTGS BY DATE
    public String getTTGSByDate(String data_sleep) {

        Cursor cursor = null;
        String ttgs_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT timeToGetSleep FROM sleep WHERE date = '" + data_sleep + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                ttgs_z_dnia = cursor.getString(cursor.getColumnIndex("timeToGetSleep"));
            }
            return ttgs_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //----- GET TOS BY DATE
    public String getTOSByDate(String data_sleep) {

        Cursor cursor = null;
        String tos_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT timeOfSleep FROM sleep WHERE date = '" + data_sleep + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                tos_z_dnia = cursor.getString(cursor.getColumnIndex("timeOfSleep"));
            }
            return tos_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //----- GET NOA BY DATE
    public String getNOAByDate(String data_sleep) {

        Cursor cursor = null;
        String noa_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT numberOfAwakes FROM sleep WHERE date = '" + data_sleep + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                noa_z_dnia = cursor.getString(cursor.getColumnIndex("numberOfAwakes"));
            }
            return noa_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //------PHYS
//add basic data
    public void addPhys(Phys phys) {
        Log.d("addPhys", phys.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();


        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_PHYS, phys.getDate());
        values.put(KEY_SCALE_PHYS, phys.getScalePhys());
        values.put(KEY_WALK, phys.getWalk());
        values.put(KEY_BREAKS, phys.getWalk());
        values.put(KEY_ADTACTIVITY, phys.getAdtActivity());


        // 3. insert
        db.insert(TABLE_PHYS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    //----- is phys with specific date exist
    public boolean isExistingPhys(String formatted_date) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query_check_exist = "SELECT * FROM phys WHERE date = '" + formatted_date + "'";
        Cursor cursor = db.rawQuery(query_check_exist, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    //----- GET SCP BY DATE
    public String getSCPByDate(String data_phys) {

        Cursor cursor = null;
        String scp_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT scale_phys FROM phys WHERE date = '" + data_phys + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                scp_z_dnia = cursor.getString(cursor.getColumnIndex("scale_phys"));
            }
            return scp_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //----- GET SCP BY DATE
    public String getWalkByDate(String data_phys) {

        Cursor cursor = null;
        String walk_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT walk FROM phys WHERE date = '" + data_phys + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                walk_z_dnia = cursor.getString(cursor.getColumnIndex("walk"));
            }
            return walk_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //----- GET BREAKS BY DATE
    public String getBreaksByDate(String data_phys) {

        Cursor cursor = null;
        String breaks_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT breaks FROM phys WHERE date = '" + data_phys + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                breaks_z_dnia = cursor.getString(cursor.getColumnIndex("breaks"));
            }
            return breaks_z_dnia;
        } finally {
            cursor.close();
        }
    }

    //----- GET BREAKS BY DATE
    public String getADTAByDate(String data_phys) {

        Cursor cursor = null;
        String ADTA_z_dnia = new String();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT adtActivity FROM phys WHERE date = '" + data_phys + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                ADTA_z_dnia = cursor.getString(cursor.getColumnIndex("adtActivity"));
            }
            return ADTA_z_dnia;
        } finally {
            cursor.close();
        }
    }


}
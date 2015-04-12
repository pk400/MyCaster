package com.example.joel.mycaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel on 4/11/2015.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataset/";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyCaster";
    private static final String TABLE_LOCATIONS = "Locations";

    private static final String KEY_ID = "Id";
    private static final String KEY_CODE = "Code";
    private static final String KEY_NAMEEN = "NameEN";
    private static final String KEY_NAMEFR = "NameFR";
    private static final String KEY_PROVINCE = "ProvinceCode";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATIONS_TABLE =
                "CREATE TABLE " + TABLE_LOCATIONS + "("
                        + KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_CODE + " TEXT UNIQUE,"
                        + KEY_NAMEEN + " TEXT,"
                        + KEY_NAMEFR + " TEXT,"
                        + KEY_PROVINCE + " TEXT"
                + ")";
        db.execSQL(CREATE_LOCATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        onCreate(db);
    }

    // CRUD STUFF
    public void addLocation(LocationXMLData data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CODE, data.getCode());
        values.put(KEY_NAMEEN, data.getNameEN());
        values.put(KEY_NAMEFR, data.getNameFR());
        values.put(KEY_PROVINCE, data.getProvinceCode());

        db.insert(TABLE_LOCATIONS, null, values);
        db.close();
    }

    public LocationXMLData getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATIONS,
                new String[] { KEY_ID, KEY_CODE, KEY_NAMEEN, KEY_NAMEFR, KEY_PROVINCE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationXMLData data = new LocationXMLData(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        return data;
    }

    public List<LocationXMLData> getAllLocations() {
        List<LocationXMLData> locList = new ArrayList<LocationXMLData>();

        String selectQuery = "SELECT * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                LocationXMLData data = new LocationXMLData();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setCode(cursor.getString(1));
                data.setNameEN(cursor.getString(2));
                data.setNameFR(cursor.getString(3));
                data.setProvinceCode(cursor.getString(4));
                locList.add(data);
            } while(cursor.moveToNext());
        }

        return locList;
    }

    public int getLocationCount() {
        String countQuery = "SELECT * FROM " + TABLE_LOCATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateLocation(LocationXMLData data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CODE, data.getCode());
        values.put(KEY_NAMEEN, data.getNameEN());
        values.put(KEY_NAMEFR, data.getNameFR());

        return db.update(TABLE_LOCATIONS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(data.getCode())});
    }

    public void deleteLocation(LocationXMLData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, KEY_ID + " = ?",
                new String[] { String.valueOf(data.getID()) });
        db.close();
    }

}

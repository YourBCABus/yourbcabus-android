package com.yourbcabus.yourbcabus_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "busInfo";
    private static final String TABLE_BUSES = "buses";

    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_INVALIDATETIME = "invalidateTime";
    private static final String KEY_ID = "id";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BUSES + "("
                + KEY_NAME + "genericName"
                + KEY_LOCATION + "gernericLocation"
                + KEY_INVALIDATETIME + "Today"
                + KEY_ID + "genericID" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSES);
        onCreate(db);
    }

    public void addBus(Bus bus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, bus.getName());
        values.put(KEY_LOCATION, bus.getLocation());
        values.put(KEY_INVALIDATETIME, bus.getInvalidateTime());
        values.put(KEY_ID, bus.getId());

        db.insert(TABLE_BUSES, null, values);
        db.close();
    }

    public Bus getBus(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BUSES, new String[]{KEY_NAME, KEY_LOCATION, KEY_INVALIDATETIME, KEY_ID},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Bus bus = new Bus(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));

        return bus;
    }

    public List<Bus> getAllBuses() {
        List<Bus> busList = new ArrayList<Bus>();
        String selectQuery = "SELECT * FROM " + TABLE_BUSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Bus bus = new Bus(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                busList.add(bus);
            } while (cursor.moveToNext());
        }

        return busList;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUSES, null, null);
        db.close();
    }
}

package com.database.databaseclass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {

    private static DataBaseManager instance;
    private DataBaseHelper databaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    private DataBaseManager(Context context) {
        databaseHelper = new DataBaseHelper(context);
        openDatabase();
    }

    private void openDatabase() {
        mSqLiteDatabase = databaseHelper.getWritableDatabase();
    }


    public static void initDatabase(Context context) {
        if (instance == null) {
            instance = new DataBaseManager(context);
        }
    }

    public static DataBaseManager getInstance() {
        return instance;
    }

    // inserting data
    public void insertData(String name, String phone, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COLUMN_NAME, name);
        contentValues.put(DataBaseHelper.COLUMN_PHONE, phone);
        contentValues.put(DataBaseHelper.COLUMN_PASSWORD, password);
        mSqLiteDatabase.insert(DataBaseHelper.TABLE_NAME, null, contentValues);
    }

    // cursor to fetch id
    public Cursor fetchId(String name, String password)
    {
        String[] projection = {DataBaseHelper.COLUMN_ID};
        String selection = DataBaseHelper.COLUMN_NAME + " = '" + name + "' AND " + DataBaseHelper.COLUMN_PASSWORD + " = '" + password +"'";
        return mSqLiteDatabase.query(DataBaseHelper.TABLE_NAME, projection, selection,null,null,null,null,null);
    }

    // to fetch the details of the particular id
    public Cursor fetchData(int id)
    {
        Cursor cursor;
        String[] projection = {DataBaseHelper.COLUMN_NAME, DataBaseHelper.COLUMN_PHONE, DataBaseHelper.COLUMN_PASSWORD};
        String selection = DataBaseHelper.COLUMN_ID + " = '" + id + " '";
        cursor =  mSqLiteDatabase.query(DataBaseHelper.TABLE_NAME, projection, selection, null, null,null, null, null);
        return cursor;


    }

    // to update the values
    public void updateData(int id, String name, String phone, String password)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COLUMN_NAME, name);
        contentValues.put(DataBaseHelper.COLUMN_PHONE, phone);
        contentValues.put(DataBaseHelper.COLUMN_PASSWORD, password);
        mSqLiteDatabase.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper.COLUMN_ID  + " = ?", new String[] {""+ id});

    }

    // to delete any record
    public void deleteRecord(int id)
    {
        mSqLiteDatabase.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.COLUMN_ID + " = ?", new String[] {""+ id});

    }

}

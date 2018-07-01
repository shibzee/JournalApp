package com.journalapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.journalapp.database.model.Journal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 26/06/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "journal.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // create notes table
        sqLiteDatabase.execSQL(Journal.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Journal.TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }
    public long insertJournal(String thought,String feeling) {
        // get writable database as we want to write data

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Journal.COLUMN_THOUGHT, thought);
        values.put(Journal.COLUMN_FEELING,feeling);

        // insert row
        long id = db.insert(Journal.TABLE_NAME, null, values);

        // close db connection
//        db.close();

        // return newly inserted row id
        return id;
    }


    public Journal getJournal(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Journal.TABLE_NAME,
//                new String[]{Journal.COLUMN_ID, Journal.COLUMN_THOUGHT,Journal.COLUMN_FEELING,Journal.COLUMN_TIMESTAMP},
                null,
                Journal.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare journal object
        Journal journal = new Journal(
                cursor.getInt(cursor.getColumnIndex(Journal.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Journal.COLUMN_THOUGHT)),
                cursor.getString(cursor.getColumnIndex(Journal.COLUMN_FEELING)),
                cursor.getString(cursor.getColumnIndex(Journal.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return journal;
    }

//    public Cursor getJournal() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = new String[] { Journal.COLUMN_ID, Journal.COLUMN_THOUGHT, Journal.COLUMN_FEELING,Journal.COLUMN_TIMESTAMP };
//        Cursor cursor = db.query(Journal.TABLE_NAME, columns, null, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }
    public List<Journal> getAllJournals() {
        List<Journal> journals = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Journal.TABLE_NAME + " ORDER BY " +
                Journal.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Journal journal= new Journal();
                journal.setId(cursor.getInt(cursor.getColumnIndex(Journal.COLUMN_ID)));
                journal.setThought(cursor.getString(cursor.getColumnIndex(Journal.COLUMN_THOUGHT)));
                journal.setFeeling(cursor.getString(cursor.getColumnIndex(Journal.COLUMN_FEELING)));
                journal.setTimestamp(cursor.getString(cursor.getColumnIndex(Journal.COLUMN_TIMESTAMP)));

                journals.add(journal);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return journals list
        return journals;
    }

    public int getJournalsCount() {
        String countQuery = "SELECT  * FROM " + Journal.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


    //Updating Journals
    public int updateJournal(Journal journal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Journal.COLUMN_THOUGHT, journal.getThought());
        values.put(Journal.COLUMN_FEELING,journal.getFeeling());

        // updating row
        return db.update(Journal.TABLE_NAME, values, Journal.COLUMN_ID + " = ?",
                new String[]{String.valueOf(journal.getId())});
    }
//
//    public int update(Journal journal){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Journal.COLUMN_THOUGHT, journal.getThought());
//        values.put(Journal.COLUMN_FEELING,journal.getFeeling());
//    }


    public boolean updateData(String id,String thought,String feeling) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
    //    contentValues.put(Journal.COLUMN_ID,id);
        contentValues.put(Journal.COLUMN_THOUGHT,thought);
        contentValues.put(Journal.COLUMN_FEELING,feeling);
      //  contentValues.put(COL_4,marks);
        db.update(Journal.TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    //Deleting Journals
    public void deleteJournal(Journal journal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Journal.TABLE_NAME, Journal.COLUMN_ID + " = ?",
                new String[]{String.valueOf(journal.getId())});
        db.close();
    }
}

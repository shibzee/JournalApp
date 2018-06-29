package com.journalapp.database.model;

/**
 * Created by root on 26/06/18.
 */

public class Journal {
    public static final String TABLE_NAME = "journal";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_THOUGHT = "thought";
    public static final String COLUMN_FEELING= "feeling";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_THOUGHT + " TEXT,"
                    + COLUMN_FEELING + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Journal(String mThought, String mFeeling) {
        this.mThought = mThought;
        this.mFeeling = mFeeling;
    }

    private int id;
    private String mThought;
    private String mFeeling;
    private String mTimestamp;

    public Journal() {
    }

    public Journal(int id, String thought, String feeling, String timestamp) {
        this.id = id;
        this.mThought = thought;
        this.mFeeling = feeling;
        this.mTimestamp = timestamp;
    }
    public Journal(String thought, String feeling, String timestamp) {
        this.mThought = thought;
        this.mFeeling = feeling;
        this.mTimestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThought() {
        return mThought;
    }

    public void setThought(String thought) {
        this.mThought = thought;
    }

    public String getFeeling() {
        return mFeeling;
    }

    public void setFeeling(String feeling) {
        this.mFeeling = feeling;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String timestamp) {
        this.mTimestamp = timestamp;
    }



}

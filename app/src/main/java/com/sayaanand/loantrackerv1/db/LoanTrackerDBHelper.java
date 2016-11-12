package com.sayaanand.loantrackerv1.db;

import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.sayaanand.loantrackerv1.vo.LoanInfo;

/**
 * Created by Nandkishore.Powar on 16/01/2016.
 */
public class LoanTrackerDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LoanTracker.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LoanTrackerDBContract.LoanDetails.TABLE_NAME + " (" +
                    LoanTrackerDBContract.LoanDetails.COLUMN_NAME_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    //LoanTrackerDBContract.LoanDetails.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    LoanTrackerDBContract.LoanDetails.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    LoanTrackerDBContract.LoanDetails.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    LoanTrackerDBContract.LoanDetails.COLUMN_NAME_PRINCIPAL + DOUBLE_TYPE + COMMA_SEP +
                    LoanTrackerDBContract.LoanDetails.COLUMN_NAME_INTEREST + DOUBLE_TYPE + COMMA_SEP +
                    LoanTrackerDBContract.LoanDetails.COLUMN_NAME_TENURE + DOUBLE_TYPE + COMMA_SEP +
                    LoanTrackerDBContract.LoanDetails.COLUMN_NAME_EMI_DATE + TEXT_TYPE +  " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LoanTrackerDBContract.LoanDetails.TABLE_NAME;

    public LoanTrackerDBHelper(Context context){
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insert(LoanInfo loanInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoanTrackerDBContract.LoanDetails.COLUMN_NAME_NAME, loanInfo.getName());
        contentValues.put(LoanTrackerDBContract.LoanDetails.COLUMN_NAME_TYPE, loanInfo.getType());
        contentValues.put(LoanTrackerDBContract.LoanDetails.COLUMN_NAME_PRINCIPAL, loanInfo.getPrincipal());
        contentValues.put(LoanTrackerDBContract.LoanDetails.COLUMN_NAME_INTEREST, loanInfo.getInterst());
        contentValues.put(LoanTrackerDBContract.LoanDetails.COLUMN_NAME_TENURE, loanInfo.getTenure());
        contentValues.put(LoanTrackerDBContract.LoanDetails.COLUMN_NAME_EMI_DATE, loanInfo.getEmiDateStr());
        db.insert(LoanTrackerDBContract.LoanDetails.TABLE_NAME, null, contentValues);
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, LoanTrackerDBContract.LoanDetails.TABLE_NAME);
        return numRows;
    }
}

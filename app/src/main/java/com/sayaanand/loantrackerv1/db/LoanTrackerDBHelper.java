package com.sayaanand.loantrackerv1.db;

import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.sayaanand.loantrackerv1.utils.LoggerUtils;
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

    private static final String SQL_SELECT_ENTRIES = "SELECT * FROM "+ LoanTrackerDBContract.LoanDetails.TABLE_NAME;
    private static final String SQL_SELECT_ENTRY_WHERE = " WHERE " +LoanTrackerDBContract.LoanDetails.COLUMN_NAME_ID +" = ?";

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

    public List<LoanInfo> getAllLoans() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_SELECT_ENTRIES, new String[]{});

        List<LoanInfo> loans = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return loans;
        }

        do {
            LoanInfo loanInfo = getLoanInfo(cursor);
            LoggerUtils.logInfo("adding "+loanInfo);
            loans.add(loanInfo);
        }while (cursor.moveToNext());
        return loans;
    }

    @NonNull
    private LoanInfo getLoanInfo(Cursor cursor) {
        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setId(cursor.getInt(0));
        loanInfo.setName(cursor.getString(1));
        loanInfo.setType(cursor.getString(2));
        loanInfo.setPrincipal(cursor.getDouble(3));
        loanInfo.setInterst(cursor.getDouble(4));
        loanInfo.setTenure(cursor.getDouble(5));
        loanInfo.setEmiDateStr(cursor.getString(6));
        return loanInfo;
    }

    public LoanInfo select(Integer loanId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_SELECT_ENTRIES+SQL_SELECT_ENTRY_WHERE, new String[]{loanId.toString()});
        if (!cursor.moveToFirst()) {
            return null;
        }
        LoanInfo loanInfo = getLoanInfo(cursor);
        return loanInfo;
    }
}

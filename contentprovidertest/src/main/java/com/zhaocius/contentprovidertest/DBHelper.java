package com.zhaocius.contentprovidertest;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhaozi on 17-12-16.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="employee.db";
    private static final int DATABASE_VERSION=1;
    public static final String EMPLOYEE_TABLE_NAME="employee";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EMPLOYEE_TABLE_NAME + " ("
                + EmployeeInfo.ID + " INTEGER PRIMARY KEY,"
                + EmployeeInfo.NAME + " TEXT,"
                + EmployeeInfo.PASSWORD + " TEXT,"
                + EmployeeInfo.AGE + " INTEGER"
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS employee");
        onCreate(db);
    }
}

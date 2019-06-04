package com.zhaocius.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by zhaozi on 17-12-16.
 */

public class EmployeeProvider extends ContentProvider {
    private DBHelper mDBHelper;
    private static final UriMatcher mUriMatcher;
    private static final int EMPLOYEE=1;
    private static final int EMPLOYEE_ID=2;
    private static HashMap<String,String> employeeMap;
    static{
        mUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(EmployeeInfo.AUTHORITY,"employee",EMPLOYEE);
        mUriMatcher.addURI(EmployeeInfo.AUTHORITY,"employee/#",EMPLOYEE_ID);
        employeeMap=new HashMap<String,String>();
        employeeMap.put(EmployeeInfo.ID,EmployeeInfo.ID);
        employeeMap.put(EmployeeInfo.NAME,EmployeeInfo.NAME);
        employeeMap.put(EmployeeInfo.PASSWORD,EmployeeInfo.PASSWORD);

    }
    @Override
    public boolean onCreate() {
        mDBHelper=new DBHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (mUriMatcher.match(uri)) {
            // 查询所有
            case EMPLOYEE:
                qb.setTables(DBHelper.EMPLOYEE_TABLE_NAME);
                qb.setProjectionMap(employeeMap);
                break;
            // 根据ID查询
            case EMPLOYEE_ID:
                qb.setTables(DBHelper.EMPLOYEE_TABLE_NAME);
                qb.setProjectionMap(employeeMap);
                qb.appendWhere(EmployeeInfo.ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Uri错误！ " + uri);
        }

        // 使用默认排序
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = EmployeeInfo.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // 获得数据库实例
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        // 返回游标集合
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long rowId = db.insert(DBHelper.EMPLOYEE_TABLE_NAME, EmployeeInfo.NAME, values);
        if (rowId > 0) {
            Uri empUri = ContentUris.withAppendedId(EmployeeInfo.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(empUri, null);
            return empUri;
        }
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        // 获得数据库实例
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        // 获得数据库实例
        int count;
        switch (mUriMatcher.match(uri)) {
            // 根据指定条件删除
            case EMPLOYEE:
                count = db.delete(DBHelper.EMPLOYEE_TABLE_NAME, selection, selectionArgs);
                break;
            // 根据指定条件和ID删除
            case EMPLOYEE_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.delete(DBHelper.EMPLOYEE_TABLE_NAME, EmployeeInfo.ID + "=" + noteId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("错误的 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        // 获得数据库实例
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;
        switch (mUriMatcher.match(uri)) {
            // 根据指定条件更新
            case EMPLOYEE:
                count = db.update(DBHelper.EMPLOYEE_TABLE_NAME, values, selection, selectionArgs);
                break;
            // 根据指定条件和ID更新
            case EMPLOYEE_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.update(DBHelper.EMPLOYEE_TABLE_NAME, values, EmployeeInfo.ID + "=" + noteId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("错误的 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

package com.zhaocius.contentprovidertest;

import android.net.Uri;

/**
 * Created by zhaozi on 17-12-16.
 */

public class EmployeeInfo {
    //DataField
    public static final String ID="id";
    public static final String NAME="name";
    public static final String PASSWORD="password";
    public static final String AGE="age";

    public static final String AUTHORITY="com.zhaocius.contentprovidertest.employee";
    public static final String DEFAULT_SORT_ORDER="id DESC";
    public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY+"employee");
    public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd.com.zhaocius.employee";
    public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd.com.zhaocius.employee";

}

package com.example.jhtwl.safty360.Dao;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jhtwl on 16/8/19.
 */
public class BlackNumberOpenHelper extends SQLiteOpenHelper {


    public BlackNumberOpenHelper(Context context) {
        super(context, "safe.db", null, 1);
    }

    // blacknumber 表名  _id 主键自动增长 number 电话号码  mode 拦截模式
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table blacknumber (_id integer primary key autoincrement,number varchar(20),mode varchar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

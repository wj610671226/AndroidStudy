package com.example.jhtwl.safty360.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.example.jhtwl.safty360.Bean.BlackNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhtwl on 16/8/19.
 */
public class BlackNumberDao {
    public BlackNumberOpenHelper helper;

    public  BlackNumberDao(Context context) {
        helper = new BlackNumberOpenHelper(context);
    }

    // number：黑名单号码  mode: 拦截模式
    public boolean add(String number, String mode) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("mode", mode);
        long rowid = database.insert("blacknumber", null, contentValues);
        if (rowid == -1) {
            return false;
        } else {
            return true;
        }
    }

    // 通过电话号码删除
    public boolean delete(String number) {
        SQLiteDatabase database = helper.getWritableDatabase();
        int rowNumber = database.delete("blacknumber", "number=?", new String[] {number});
        if (rowNumber == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 通过电话号码修改拦截的模式
    public boolean changeNumberMode(String number, String mode) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mode", mode);
        int rownumber = database.update("blacknumber", values, "number=?", new String[] {number});
        if (rownumber == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 返回一个黑名单拦截模式
    public String findNumber(String number) {
        String mode = "";
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query("blacknumber", new String[] {"mode"},"number=?", new String[]{number}, null, null, null);
        if (cursor.moveToNext()) {
            mode = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return mode;
    }

    // 查询所有黑名单
    public List<BlackNumber> findAll() {
        SQLiteDatabase database = helper.getReadableDatabase();
        List<BlackNumber> blackNumbers = new ArrayList<BlackNumber>();
        Cursor cursor = database.query("blacknumber", new String[] {"number", "mode"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BlackNumber blackNumber = new BlackNumber();
            blackNumber.setNumber(cursor.getString(0));
            blackNumber.setMode(cursor.getString(1));
            blackNumbers.add(blackNumber);
        }
        cursor.close();
        database.close();

        SystemClock.sleep(3000);
        return blackNumbers;
    }

    // 分页加载数据
    public List<BlackNumber> findPar(int pageNumber, int pageSize) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select number, mode from blacknumber limit ? offset ?", new String[] {String.valueOf(pageSize),
        String.valueOf(pageSize * pageNumber)});
        List<BlackNumber> blackNumbers = new ArrayList<BlackNumber>();
        while (cursor.moveToNext()) {
            BlackNumber blackNumber = new BlackNumber();
            blackNumber.setMode(cursor.getString(1));
            blackNumber.setNumber(cursor.getString(0));
            blackNumbers.add(blackNumber);
        }
        cursor.close();
        database.close();
        return blackNumbers;
    }

    // 分批加载数据
    public List<BlackNumber> findPr2(int startIndex, int maxCount) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select number,mode from blacknumber limit ? offset ?", new String[]{String.valueOf(maxCount),
                String.valueOf(startIndex)});
        List<BlackNumber> blackNumbers = new ArrayList<BlackNumber>();
        while (cursor.moveToNext()) {
            BlackNumber blackNumber = new BlackNumber();
            blackNumber.setMode(cursor.getString(1));
            blackNumber.setNumber(cursor.getString(0));
            blackNumbers.add(blackNumber);
        }
        cursor.close();
        database.close();
        return blackNumbers;
    }

    // 获取总的记录数
    public int getTotleNumber() {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select count(*) from blacknumber", null);
        cursor.moveToNext();
        int count = cursor.getInt(0);
        cursor.close();
        database.close();
        return count;
    }
}

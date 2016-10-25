package com.jhtwl.googlestore.Helper;

import android.content.ContentValues;
import android.database.Cursor;

import com.jhtwl.googlestore.Utils.UiUtils;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/24  上午9:51
 */
public class DataBaseDao {
    // 增
    public static long insert() {
        DataBaseHelper helper = new DataBaseHelper(UiUtils.getContext(), "myTestDB.db3",null, 1);
        // 插入数据
//        helper.getWritableDatabase().execSQL("insert into dict values(null, ?, ?)", new String[] {"hello", "word"});

        for (int i = 0; i < 30; i ++) {
            ContentValues values = new ContentValues();
            values.put("word", "zhangsan" + "---- i");
            values.put("detail", "lishi" +  "---- i" );
            helper.getWritableDatabase().insert("dict", null, values);
        }

        return 1;
    }

    // 改
    public static long update() {
        DataBaseHelper helper = new DataBaseHelper(UiUtils.getContext(), "myTestDB.db3",null, 1);
        ContentValues values = new ContentValues();
        values.put("word", "修改word");
        values.put("detail", "修改detail");
         return helper.getWritableDatabase().update("dict", values, " _id = ?", new String[] {"1"});
    }

    // 删
    public static long delete() {
        DataBaseHelper helper = new DataBaseHelper(UiUtils.getContext(), "myTestDB.db3",null, 1);

        return helper.getWritableDatabase().delete("dict","_id = ?", new String[] {"3"});
    }

    // 查
    public static long quary() {
        DataBaseHelper helper = new DataBaseHelper(UiUtils.getContext(), "myTestDB.db3",null, 1);
        Cursor cursor = helper.getWritableDatabase().query("dict",new String[]{"_id, word, detail"},null, null,null, null,null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String word = cursor.getString(1);
            String detail = cursor.getString(2);
            System.out.println("id = " + id + "  word = " + word + "    detail = " + detail );
        }
        return 1;
    }
}

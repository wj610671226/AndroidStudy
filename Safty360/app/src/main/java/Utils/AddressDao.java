package Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jhtwl on 16/8/17.
 */
public class AddressDao {

    public static String getAddress(String number, Context context) {
        String address = "未知号码";

//        String path = Environment.getExternalStorageDirectory() + "files/address.db";
        String path = "data/data/com.example.jhtwl.safty360/files/address.db";
        boolean isCopyDataBase = copyDataBase(path, context);

        if (isCopyDataBase) {
            // 获取数据库对象
            SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

            if (number.matches("^1[3-8]\\d{9}$")) { // 匹配手机号码
                Cursor cursor = database.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)",
                        new String[]{number.substring(0, 7)});

                if (cursor.moveToNext()) {
                    address = cursor.getString(0);
                }
                cursor.close();
            } else if (number.matches("^\\d+$")) {// 匹配数字
                switch (number.length()) {
                    case 3:
                        address = "报警电话";
                        break;
                    case 4:
                        address = "模拟器";
                        break;
                    case 5:
                        address = "客服电话";
                        break;
                    case 7:
                    case 8:
                        address = "本地电话";
                        break;
                    default:
                        // 01088881234
                        // 048388888888
                        if (number.startsWith("0") && number.length() > 10) {// 有可能是长途电话
                            // 有些区号是4位,有些区号是3位(包括0)

                            // 先查询4位区号
                            Cursor cursor = database.rawQuery(
                                    "select location from data2 where area =?",
                                    new String[] { number.substring(1, 4) });

                            if (cursor.moveToNext()) {
                                address = cursor.getString(0);
                            } else {
                                cursor.close();

                                // 查询3位区号
                                cursor = database.rawQuery(
                                        "select location from data2 where area =?",
                                        new String[] { number.substring(1, 3) });

                                if (cursor.moveToNext()) {
                                    address = cursor.getString(0);
                                }

                                cursor.close();
                            }
                        }
                        break;
                }
            }
            database.close();

        }
        return address;
    }

    private static boolean copyDataBase(String path, Context context) {
        if (!new File(path).exists()) {
            try {
                FileOutputStream outputStream = new FileOutputStream(path);
                InputStream inputStream = context.getAssets().open("address.db");
                byte buffer[] = new byte[1024];
                int readBytes = 0;
                while ((readBytes = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0 , readBytes);
                }
                inputStream.close();
                outputStream.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

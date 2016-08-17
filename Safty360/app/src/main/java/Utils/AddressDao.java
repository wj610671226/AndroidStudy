package Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Created by jhtwl on 16/8/17.
 */
public class AddressDao {

    public static String getAddress(String number) {
        String address = "未知号码";

        String path = Environment.getExternalStorageDirectory() + "files/address.db"; // 数据库存放早工程目录那个地方
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
        return address;
    }
}

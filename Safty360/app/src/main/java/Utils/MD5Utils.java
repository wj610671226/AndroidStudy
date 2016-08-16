package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jhtwl on 16/7/29.
 */
public class MD5Utils {
    /*
    *   MD5加密
    * */
    public static String md5Encode(String password) {
        try {
            // 获取MD5算法对象
            MessageDigest instance = MessageDigest.getInstance("MD5");
            // 对字符串加密，返回字节数组
            byte digest[] = instance.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            for (byte b: digest) {
                // 获取字节的低八位有效值
                int i = b & 0xff;
                // 将整体转为16进制
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    // 如果是1位的话，补0
                    hexString = "0" + hexString;
                }
                buffer.append(hexString);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

package Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jhtwl on 16/7/27.
 */
public class StreamUtils {
    /*
    *  将输入流读取成String后返回
    * */
    public static String readFromStream(InputStream inputStream) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte buffer[] = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        String result = outputStream.toString();
        inputStream.close();
        outputStream.close();
        return result;
    }
}

package com.example.jhtwl.accesssocket;

/**
 * Created by jhtwl on 16/8/22.
 */
public class CRC16 {

    private static int CRC_POLYNOM = 0x8408;
    private static int CRC_PRESET = 0xFFFF;
    public static int getCRC16(int data[], int count) {
        int crc = CRC_PRESET;
        for (int i = 0; i < count; i ++) {
            crc ^= data[i];
            for (int j = 0; j < 8; j ++) {
                int crc1 = crc;
                crc1 &= 0x0001;
                if (crc1 > 0) {
                    crc = (crc >> 1) ^ CRC_POLYNOM;
                } else {
                    crc = (crc >> 1);
                }
            }
        }
        return crc;
    }
}

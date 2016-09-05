package com.example.jhtwl.accesssocket;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private boolean isResolveData = false;
    private int gs_ConAddr = 0x00;
    private static int totleCount = 0;

    private TextView tv_number;
    private ListView lv_listView;

    private ArrayList<String> list;
    private MyAdapter adapter;

    private Timer timer;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("----------handleMessage------------");
            tv_number.setText(totleCount + "人");
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        int crc16 = CRC16.getCRC16(new int[]{0x0B, 0x00, 0x42, 1, 10, 1, 1, 10, 1}, 9);

        initUI();
        initSocket();
    }

    private void initUI() {

        list = new ArrayList<String>();
        tv_number = (TextView) findViewById(R.id.tv_number);
        lv_listView = (ListView) findViewById(R.id.lv_list);
        adapter = new MyAdapter();
        lv_listView.setAdapter(adapter);
    }

    static class ViewHolder {
        TextView tv_uid;
    }
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(MainActivity.this, R.layout.list_item_layout, null);
                holder.tv_uid = (TextView) view.findViewById(R.id.tv_uid);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_uid.setText(list.get(i));
            return view;
        }
    }

    public void button7(View view){
        int crc16 = CRC16.getCRC16(new int[]{0x0B, 0x00, 0x42, 1, 10, 1, 1, 10, 1}, 9);
        byte gs_SocketSendBuffer[] = new byte[]{0x0B, 0x00, 0x42, 1, 10, 1, 1, 10, 1, (byte) (crc16 & 0xFF), (byte) ((crc16 >> 8) & 0xFF)};
        try {
            Util.outputStream = mSocket.getOutputStream();
            Util.outputStream.write(gs_SocketSendBuffer);
            Util.outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 设置或读取控制器地址
    public void button1(View view){

        // 设置或读取控制器地址
        RFID_SetControlAddrCMD(1, 0);
        SystemClock.sleep(300);
        RFID_RedrayControlCMD(1);
        SystemClock.sleep(300);
        RFID_ClearChannelDataCMD();
        SystemClock.sleep(300);
        RFID_ClearAllBackgroundDateCMD();
        SystemClock.sleep(300);
        startTimerTask();
    }

    // 开关红外探测
    public void button2(View view){
        // 2、开关红外探测
        RFID_RedrayControlCMD(1);
    }

    // 删除控制器队列中的UID及通过人数
    public void button3(View view){
        // 3、删除控制器队列中的UID及通过人数
        RFID_ClearChannelDataCMD();
    }

    // 4、删除所有的后台记录数据
    public void button4(View view){
        // 4、删除所有的后台记录数据
        RFID_ClearAllBackgroundDateCMD();


    }

    //5、获取通道管理信息获取卡片UID和相关消息
    public void button5(View view){
        // 5、获取通道管理信息获取卡片UID和相关消息
        while (true) {
            try {
                System.out.println("获取通道管理信息获取卡片UID和相关消息");
                isResolveData = true;

                RFID_SendReadChannelCMD();
                Thread.sleep(1000);

//                if (totleCount == 5) {
//                    return;
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startTimerTask() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                isResolveData = true;
                RFID_SendReadChannelCMD();
            }

        };
        if (timer == null) {
            timer = new Timer();
            timer.schedule(task, 0, 1000);
        }
    }

    public void button6(View view){
        // 叫
//        RFID_LedBeeCtrlCMD();
    }

    private void initSocket() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = Util.getSocketInstacne();
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    int len = 0;
                    byte buffer[] = new byte[1024];
                    while ((len = mSocket.getInputStream().read(buffer)) != -1) {
                        output.write(buffer, 0, len);
                        byte result[] = output.toByteArray();
                        if (result.length == result[0]) {
                            int newResult[] = new int[result.length];
                            for (int i = 0; i < result.length; i ++) {
                                newResult[i] = result[i] & 0xFF;
                            }
                            // 打印数据
//                            System.out.println(Arrays.toString(result));
                            System.out.println(Arrays.toString(newResult));
                            if (isResolveData) {
                                // 解析数据
                                int data[] = RFID_GetChannelMessage(newResult);
                                checkResult(data, newResult);
                            }

                            // 清空上次的数据
                            output.reset();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void checkResult(int resultCode[], int allData[]) { // 返回的数组数据 依次是cmdRet  msgType

        // 19 0 1 0 0 0 0 0 0 0 0 16 8 25 9 28 46 56 13
        int cmdRet = resultCode[0];
        int msgType = resultCode[1];

        String UID = "";
        if (cmdRet == -2) {
            return;
        }
        if (cmdRet == 0 && msgType == 0) { //常规消息
            int inOutFlag;
            String InOrOut = "";
            String DateTime = "";

            for (int i = 3; i < 11; i++) {
                int id = allData[i];
                // 转16进制
                String dataString = Integer.toHexString(id);
                if (dataString.length() < 2) {
                    UID += ("0" + dataString);
                } else {
                    UID += dataString;
                }
            }

            System.out.println("UID ====" + UID);
            System.out.println(allData);

            inOutFlag = allData[11];
            switch (inOutFlag & 0x0F) {
                case 1:
                    InOrOut = "正向";
                    break;
                case 2:
                    InOrOut = "反向";
                    break;
                case 3:
                    InOrOut = "方向不定";
                    break;
                case 4:
                    InOrOut = "红外已经关闭";
                    break;
                default:
                    break;
            }

            for (int i = 12; i < 18; i++) {
                int date = 0;
                if (i == 12) {
                    date = allData[i] + 2000;
                } else {
                    date = allData[i];
                }
                DateTime += date + "-";
            }

            System.out.println("DateTime ====" + DateTime);

            if (UID.equals("0000000000000000")) {
                System.out.println("无人");
                System.out.println(DateTime);
            } else if (UID.equals("FFFFFFFFFFFFFFFF")) {
                System.out.println(UID);
                System.out.println(InOrOut);
                System.out.println(DateTime);
            } else if (inOutFlag == 4) {
                totleCount++;
                System.out.println("红外已经关闭");
                System.out.println(totleCount);
                RFID_LedBeeCtrlCMD();

                list.add(0, UID);
                System.out.println(list);

                System.out.println("----------------");
                handler.sendEmptyMessage(0);
                System.out.println("----------------");

            } else {
//                RFID_LedBeeCtrlCMD();
            }

            if ((inOutFlag & 0xF0) == 0x30)//有标签数据
            {
                System.out.println("有标签数据");
            }
        }
    }

    // 蜂鸣器响应 与LED灯控制
    public void RFID_LedBeeCtrlCMD() {
        int crc16 = CRC16.getCRC16(new int[]{0x0B, 0x00, 0x42, 1, 10, 1, 1, 10, 1}, 9);
        byte gs_SocketSendBuffer[] = new byte[]{0x0B, 0x00, 0x42, 1, 10, 1, 1, 10, 1, (byte) (crc16 & 0xFF), (byte) ((crc16 >> 8) & 0xFF)};
        // 发送数据
        Util.send(gs_SocketSendBuffer, getApplicationContext());
    }

    // 设置或读取控制器地址
    public void RFID_SetControlAddrCMD(int getSet, int addr) {
        int crc16 = CRC16.getCRC16(new int[]{0x07, 0x00, 0x4B, getSet, addr}, 5);
        byte gs_SocketSendBuffer[] = new byte[]{0x07, 0x00, 0x4B, (byte) getSet, (byte) addr, (byte) (crc16 & 0xFF), (byte) ((crc16 >> 8) & 0xFF)};
        // 发送数据
        Util.send(gs_SocketSendBuffer, getApplicationContext());
    }


    // 删除控制器队列中的UID及通过人数  有响应
    public void RFID_ClearChannelDataCMD() {
        int crc16 = CRC16.getCRC16(new int[]{0x05, 0x00, 0x44}, 3);
        byte gs_SocketSendBuffer[] = new byte[]{0x05, 0x00, 0x44, (byte) (crc16 & 0xFF), (byte) ((crc16 >> 8) & 0xFF)};
        Util.send(gs_SocketSendBuffer, getApplicationContext());
    }

    // 删除所有的后台记录数据
    public void RFID_ClearAllBackgroundDateCMD() {
        int crc16 = CRC16.getCRC16(new int[]{0x05, 0x00, 0x54}, 3);
        byte gs_SocketSendBuffer[] = new byte[]{0x05, 0x00, 0x54, (byte) (crc16 & 0xFF), (byte) ((crc16 >> 8) & 0xFF)};
        Util.send(gs_SocketSendBuffer, getApplicationContext());
    }

    // 开关红外探测 1 关闭 2 打开  3 查询
    public void RFID_RedrayControlCMD(int ctrlType) {
        int crc16 = CRC16.getCRC16(new int[]{0x06, gs_ConAddr, 0x49, ctrlType}, 4);
        byte gs_SocketSendBuffer[] = new byte[]{0x06, (byte) gs_ConAddr, 0x49, (byte) ctrlType, (byte) (crc16 & 0xFF), (byte) ((crc16 >> 8) & 0xFF)};
        Util.send(gs_SocketSendBuffer, getApplicationContext());
    }

    // 获取通道管理信息 获取卡片UID 和相关消息
    public void RFID_SendReadChannelCMD() {
        int crc16 = CRC16.getCRC16(new int[]{0x05, gs_ConAddr, 0x43}, 3);
        byte gs_SocketSendBuffer[] = new byte[]{0x05, (byte) gs_ConAddr, 0x43, (byte) (crc16 & 0xFF), (byte) ((crc16 >> 8) & 0xFF)};
        Util.send(gs_SocketSendBuffer, getApplicationContext());
    }

    public int[] RFID_GetChannelMessage(int allData[]) { // 返回数组 依次是cmdRet  msgType

        // 19 0 1 0 0 0 0 0 0 0 0 16 8 25 9 28 46 56 13

        // 应答
        RFID_Acknowledge();

        if (allData.length == 0) {
            return new int[]{-1, -1};
        }
        int pSocktBuffer[] = allData;
        int msgLen = pSocktBuffer[0];
        int msgType = 0xFF;

        if (msgLen == 0x14) { //常规响应
            int conAddr = pSocktBuffer[1];
            int status = pSocktBuffer[2];
            if (status == 0x00) {
                msgType = 0;
                return new int[] {0, msgType};
            } else {
                return new int[] {-1, msgType};
            }
        } else if(msgLen > 0x14) {
            int conAddr = pSocktBuffer[1];
            int status = pSocktBuffer[2];
            if (conAddr == gs_ConAddr && status == 0x00) {
                msgType = 0;
                return new int[]{0, msgType};
            } else if (conAddr == gs_ConAddr && status == 0x02) {
                msgType = 2;
                return new int[]{0, msgType};
            } else {
                return new int[]{-1, msgType};
            }
        } else {
            return new int[]{-1,msgType};
        }
    }

    // 响应命令
    public void RFID_Acknowledge() {
        int crc16 = CRC16.getCRC16(new int[]{0x05, gs_ConAddr, 0x41}, 3);
        byte gs_SocketSendBuffer[] = new byte[]{0x05, (byte) gs_ConAddr, 0x41, (byte) (crc16 & 0xFF), (byte) ((crc16 >> 8) & 0xFF)};
        // 发送数据
        Util.send(gs_SocketSendBuffer, getApplicationContext());
    }
}
